package ru.nspk.transaction.service;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.nspk.account.model.enums.BalanceOperation;
import ru.nspk.account.service.AccountService;
import ru.nspk.aop.Loggable;
import ru.nspk.bpp.Timed;
import ru.nspk.exception.InvalidDataException;
import ru.nspk.transaction.TransactionProperties;
import ru.nspk.transaction.dto.TransactionReqDto;
import ru.nspk.transaction.dto.TransactionRespDto;
import ru.nspk.transaction.event.TrxLoggerPublisher;
import ru.nspk.transaction.mapper.TransactionMapper;
import ru.nspk.transaction.model.Transaction;
import ru.nspk.transaction.storage.TransactionRepository;

@Service
@Profile("prod")
@RequiredArgsConstructor
public class TransactionServiceProd implements TransactionService {
    private final AccountService accountService;
    private final TransactionRepository transactionRepository;
    private final TransactionHistory history;
    private final Clock clock;
    private final TrxLoggerPublisher trxLoggerPublisher;
    private final TransactionMapper mapper;
    private final TransactionProperties properties;
    private long transactionIdCounter = 1;

    @Loggable
    @Timed
    public TransactionRespDto createTransaction(TransactionReqDto transactionDto) {
        var acctFrom = accountService.getAccount(transactionDto.getAccountFrom());
        var acctTo = accountService.getAccount(transactionDto.getAccountTo());
        var transaction =
                new Transaction(
                        getNextId(),
                        LocalDateTime.now(clock),
                        acctFrom.getAccountNumber(),
                        acctTo.getAccountNumber(),
                        transactionDto.getAmount(),
                        transactionDto.getCurrency(),
                        true);

        accountService.changeBalance(acctFrom, transactionDto.getAmount(), BalanceOperation.DEC);
        accountService.changeBalance(acctTo, transactionDto.getAmount(), BalanceOperation.INC);
        trxLoggerPublisher.publishEvent("Transaction created!");
        return mapper.toTransactionRespDto(transactionRepository.save(transaction));
    }

    @Loggable
    public TransactionRespDto reverseTransaction(long transactionId) {
        var transaction = getTransaction(transactionId);
        var acctFrom = accountService.getAccount(transaction.getAccountFrom());
        var acctTo = accountService.getAccount(transaction.getAccountTo());
        var reverse =
                new Transaction(
                        getNextId(),
                        LocalDateTime.now(clock),
                        transaction.getAccountFrom(),
                        transaction.getAccountTo(),
                        transaction.getAmount(),
                        transaction.getCurrency(),
                        true);

        accountService.changeBalance(acctTo, reverse.getAmount(), BalanceOperation.DEC);
        accountService.changeBalance(acctFrom, reverse.getAmount(), BalanceOperation.INC);
        trxLoggerPublisher.publishEvent("Transaction reversed!");
        return mapper.toTransactionRespDto(transactionRepository.save(reverse));
    }

    public List<TransactionRespDto> getAllHistory() {
        return mapper.toListTransactionRespDto(history.getAllHistory());
    }

    public List<TransactionRespDto> getHistory(
            Long accountNumber, LocalDate startDate, LocalDate endDate) {
        return mapper.toListTransactionRespDto(
                history.getHistory(accountNumber, startDate, endDate));
    }

    private Transaction getTransaction(long transactionId) {
        return transactionRepository
                .findById(transactionId)
                .orElseThrow(
                        () ->
                                new InvalidDataException(
                                        String.format(
                                                "There no such transaction %s", transactionId)));
    }

    private Long getNextId() {
        var prefix = String.valueOf(properties.getTrxIdPrefix());
        var currentDate = LocalDate.now(clock).format(DateTimeFormatter.ofPattern("yyMMdd"));
        int counterLength = properties.getTrxIdLength() - prefix.length() - currentDate.length();

        var format = new StringBuilder().append("%0").append(counterLength).append("d").toString();
        var trxId =
                Long.parseLong(prefix + currentDate + String.format(format, transactionIdCounter));
        transactionIdCounter++;
        return trxId;
    }
}
