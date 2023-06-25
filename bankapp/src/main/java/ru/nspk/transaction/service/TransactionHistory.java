package ru.nspk.transaction.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nspk.transaction.model.Transaction;
import ru.nspk.transaction.storage.TransactionRepository;

@RequiredArgsConstructor
@Service
public class TransactionHistory {
    protected final TransactionRepository transactionRepository;

    public List<Transaction> getAllHistory() {
        List<Transaction> transactions = new ArrayList<>();
        transactionRepository.findAll().iterator().forEachRemaining(transactions::add);
        return transactions;
    }

    public List<Transaction> getHistory(
            Long accountNumber, LocalDate startDate, LocalDate endDate) {
        return transactionRepository.findByAccountBetweenDates(accountNumber, startDate, endDate);
    }
}
