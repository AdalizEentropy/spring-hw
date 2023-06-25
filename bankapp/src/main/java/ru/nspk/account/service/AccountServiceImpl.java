package ru.nspk.account.service;

import static ru.nspk.account.model.enums.BalanceOperation.DEC;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.nspk.account.model.Account;
import ru.nspk.account.model.enums.BalanceOperation;
import ru.nspk.account.storage.AccountRepository;
import ru.nspk.bpp.Timed;
import ru.nspk.card.model.CardProgram;
import ru.nspk.card.service.CardService;
import ru.nspk.exception.InvalidDataException;
import ru.nspk.exception.NotEnoughMoneyException;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final BalanceHistoryService balanceHistoryService;
    private final CardService cardService;
    private final Clock clock;

    public void changeBalance(Account account, double amount, BalanceOperation operation) {
        var currBalance = account.getBalance();
        if (DEC.equals(operation)) {
            checkOverdraft(currBalance, amount);
            account.setBalance(currBalance - amount);
        } else {
            account.setBalance(currBalance + amount);
        }

        balanceHistoryService.saveBalanceHistoryRecord(account, LocalDateTime.now(clock));
        accountRepository.save(account);
        log.debug("Account balance {} was changed", account.getAccountNumber());
    }

    public Account getAccount(long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(
                        () ->
                                new InvalidDataException(
                                        String.format("There no such account %s", accountId)));
    }

    public Double getBalanceByDate(long accountId, LocalDate date) {
        var account = getAccount(accountId);
        Optional<Double> balanceByDate =
                balanceHistoryService.getBalanceBeforeOrEqualsDate(account, date);

        if (balanceByDate.isEmpty()) {
            return balanceHistoryService
                    .getBalanceAfterDate(account, date)
                    .orElse(account.getBalance());
        } else {
            return balanceByDate.get();
        }
    }

    @Timed
    public void createAccount(Account account) {
        balanceHistoryService.initBalanceHistory(account);
        accountRepository.save(account);
    }

    public Account addCard(Account account, CardProgram program) {
        var card = cardService.createCard(program);
        card.ifPresent(value -> account.getCards().add(value));

        return account;
    }

    private void checkOverdraft(double balance, double amount) {
        if (balance - amount < 0) {
            throw new NotEnoughMoneyException("Not enough money exception");
        }
    }
}
