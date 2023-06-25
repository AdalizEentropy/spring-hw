package ru.nspk.transaction.service;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.nspk.exception.UnsupportedMethodException;
import ru.nspk.transaction.dto.TransactionReqDto;
import ru.nspk.transaction.dto.TransactionRespDto;
import ru.nspk.transaction.mapper.TransactionMapper;

@Service
@Slf4j
@Profile({"readonly"})
@RequiredArgsConstructor
public class TransactionServiceReadOnly implements TransactionService {
    private final TransactionHistory history;
    private final TransactionMapper mapper;

    public TransactionRespDto createTransaction(TransactionReqDto transactionDto) {
        throw new UnsupportedMethodException("Unsupported method");
    }

    public TransactionRespDto reverseTransaction(long transactionId) {
        throw new UnsupportedMethodException("Unsupported method");
    }

    public List<TransactionRespDto> getAllHistory() {
        return mapper.toListTransactionRespDto(history.getAllHistory());
    }

    public List<TransactionRespDto> getHistory(Long accountNumber, LocalDate startDate, LocalDate endDate) {
        return mapper.toListTransactionRespDto(history.getHistory(accountNumber, startDate, endDate));
    }
}
