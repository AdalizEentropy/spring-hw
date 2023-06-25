package ru.nspk.transaction.service;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static ru.nspk.util.CreateTestTransactions.createDto;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.nspk.exception.UnsupportedMethodException;
import ru.nspk.transaction.mapper.TransactionMapper;
import ru.nspk.transaction.storage.TransactionRepository;

@ExtendWith(MockitoExtension.class)
class TransactionServiceReadOnlyTest {
    private TransactionService transactionService;
    @Mock private TransactionMapper mapper;
    @Mock private TransactionRepository transactionRepository;

    @BeforeEach
    void init() {
        transactionService =
                new TransactionServiceReadOnly(
                        new TransactionHistory(transactionRepository), mapper);
    }

    @Test
    void showEmptyHistory() {
        var trxDto = createDto(1234567, 7654321, 100, 643);
        assertThatExceptionOfType(UnsupportedMethodException.class)
                .isThrownBy(() -> transactionService.createTransaction(trxDto));

        transactionService.getAllHistory();
        verify(mapper).toListTransactionRespDto(argThat(List::isEmpty));
    }
}
