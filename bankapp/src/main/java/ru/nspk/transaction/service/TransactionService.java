package ru.nspk.transaction.service;

import java.time.LocalDate;
import java.util.List;
import ru.nspk.transaction.dto.TransactionReqDto;
import ru.nspk.transaction.dto.TransactionRespDto;

public interface TransactionService {

    TransactionRespDto createTransaction(TransactionReqDto transactionReqDto);

    TransactionRespDto reverseTransaction(long transactionId);

    List<TransactionRespDto> getAllHistory();

    List<TransactionRespDto> getHistory(Long accountNumber, LocalDate startDate, LocalDate endDate);
}
