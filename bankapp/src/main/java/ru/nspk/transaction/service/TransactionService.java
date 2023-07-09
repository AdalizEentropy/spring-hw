package ru.nspk.transaction.service;

import ru.nspk.transaction.dto.TransactionReqDto;
import ru.nspk.transaction.dto.TransactionRespDto;

public interface TransactionService {

    TransactionRespDto createTransaction(TransactionReqDto transactionReqDto);

    TransactionRespDto reverseTransaction(long transactionId);
}
