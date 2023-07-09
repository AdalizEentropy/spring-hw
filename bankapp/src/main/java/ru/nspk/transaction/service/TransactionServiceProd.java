package ru.nspk.transaction.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.nspk.account.model.enums.BalanceOperation;
import ru.nspk.account.service.AccountService;
import ru.nspk.aop.Loggable;
import ru.nspk.bpp.Timed;
import ru.nspk.exception.InvalidDataException;
import ru.nspk.transaction.dto.TransactionReqDto;
import ru.nspk.transaction.dto.TransactionRespDto;
import ru.nspk.transaction.event.TrxLoggerPublisher;
import ru.nspk.transaction.mapper.TransactionMapper;
import ru.nspk.transaction.model.Transaction;
import ru.nspk.transaction.storage.TransactionRepository;

import java.time.Clock;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionServiceProd implements TransactionService {
    private final AccountService accountService;
    private final TransactionRepository transactionRepository;
    private final Clock clock;
    private final TrxLoggerPublisher trxLoggerPublisher;
    private final TransactionMapper mapper;

    @Loggable
    @Timed
    @Transactional
    public TransactionRespDto createTransaction(TransactionReqDto transactionDto) {
        var acctFrom = accountService.getAccount(transactionDto.getAccountFrom());
        var acctTo = accountService.getAccount(transactionDto.getAccountTo());
        var transaction =
                new Transaction(
                        null,
                        LocalDateTime.now(clock),
                        acctFrom.getAccountNumber(),
                        acctTo.getAccountNumber(),
                        transactionDto.getAmount(),
                        transactionDto.getCurrency());

        accountService.changeBalance(acctFrom, transactionDto.getAmount(), BalanceOperation.DEC);
        accountService.changeBalance(acctTo, transactionDto.getAmount(), BalanceOperation.INC);
        trxLoggerPublisher.publishEvent("Transaction created!");
        return mapper.toTransactionRespDto(transactionRepository.save(transaction));
    }

    @Loggable
    @Transactional
    public TransactionRespDto reverseTransaction(long transactionId) {
        var transaction = getTransaction(transactionId);
        var acctFrom = accountService.getAccount(transaction.getAccountFrom());
        var acctTo = accountService.getAccount(transaction.getAccountTo());
        var reverse =
                new Transaction(
                        null,
                        LocalDateTime.now(clock),
                        transaction.getAccountFrom(),
                        transaction.getAccountTo(),
                        transaction.getAmount(),
                        transaction.getCurrency());

        accountService.changeBalance(acctTo, reverse.getAmount(), BalanceOperation.DEC);
        accountService.changeBalance(acctFrom, reverse.getAmount(), BalanceOperation.INC);
        trxLoggerPublisher.publishEvent("Transaction reversed!");
        return mapper.toTransactionRespDto(transactionRepository.save(reverse));
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
}
