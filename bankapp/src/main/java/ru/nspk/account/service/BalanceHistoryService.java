package ru.nspk.account.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import ru.nspk.account.model.Account;
import ru.nspk.account.model.BalanceHistoryRecord;
import ru.nspk.account.storage.BalanceHistoryRepository;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BalanceHistoryService {

    private final Clock clock;
    private final BalanceHistoryRepository balanceHistoryRepository;

    public Optional<Double> getBalanceBeforeOrEqualsDate(Account account, LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay().plusDays(1);
        return balanceHistoryRepository.findByAccountBeforeOrEqualsDate(
                account.getAccountNumber(), startOfDay);
    }

    public Optional<Double> getBalanceAfterDate(Account account, LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        return balanceHistoryRepository.findByAccountAfterDate(
                account.getAccountNumber(), startOfDay);
    }

    public void saveBalanceHistoryRecord(Account account, LocalDateTime time) {
        account.getBalanceHistoryRecords()
                .add(
                        new BalanceHistoryRecord(
                                account.getAccountNumber(), time, account.getBalance()));
        log.debug("Balance for account {} was saved in history", account.getAccountNumber());
    }

    public void initBalanceHistory(Account account) {
        account.getBalanceHistoryRecords()
                .add(
                        new BalanceHistoryRecord(
                                account.getAccountNumber(),
                                LocalDateTime.now(clock),
                                account.getBalance()));
    }
}
