package ru.nspk;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static ru.nspk.util.CreateTestAccounts.createAccountReceiver;
import static ru.nspk.util.CreateTestAccounts.createAccountSender;
import static ru.nspk.util.CreateTestTransactions.createDto;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;
import org.springframework.transaction.annotation.Transactional;
import ru.nspk.account.model.Account;
import ru.nspk.account.service.AccountService;
import ru.nspk.base.BasePersistenceTest;
import ru.nspk.card.model.CardProgram;
import ru.nspk.exception.InvalidDataException;
import ru.nspk.exception.NotEnoughMoneyException;
import ru.nspk.transaction.TransactionProperties;
import ru.nspk.transaction.event.TrxLoggerEvent;
import ru.nspk.transaction.service.TransactionService;

@ActiveProfiles("prod")
@RecordApplicationEvents
@Transactional
class BankAppIntegrationTest extends BasePersistenceTest {
    @Autowired AccountService accountService;
    @Autowired TransactionService transactionService;
    @Autowired TransactionProperties properties;
    @Autowired ApplicationEvents applicationEvents;
    Account acctFrom = createAccountSender();
    Account acctTo = createAccountReceiver();

    @BeforeEach
    void createAccount() {
        accountService.createAccount(acctFrom);
        accountService.createAccount(acctTo);
    }

    @Test
    void getBalanceByDate_withoutTransactions() {
        var balance = accountService.getBalanceByDate(acctFrom.getAccountNumber(), LocalDate.now());

        assertThat(balance).isEqualTo(1000.10);
    }

    @Test
    @Disabled("в рандомный момент времени кладутся записи в таблицу trans_histrory. С await() не смогла решить проблему")
    void getBalance_withTransaction() {
        var trans = transactionService.createTransaction(createDto(1234567, 7654321, 100, 643));

        assertThat(trans).isNotNull();
        assertThat(accountService.getAccount(1234567))
                .isNotNull()
                .hasFieldOrPropertyWithValue("balance", 900.10);
        assertThat(accountService.getAccount(7654321))
                .isNotNull()
                .hasFieldOrPropertyWithValue("balance", 2100.00);
        assertThat(String.valueOf(trans.getId())).hasSize(properties.getTrxIdLength()).hasSize(10);

        var balanceAfterTrx =
                accountService.getBalanceByDate(acctFrom.getAccountNumber(), LocalDate.now());

        assertThat(balanceAfterTrx).isEqualTo(900.10);

        var balanceBeforeTrx =
                accountService.getBalanceByDate(
                        acctFrom.getAccountNumber(), LocalDate.now().minusDays(1));

        assertThat(balanceBeforeTrx).isEqualTo(1000.10);
    }

    @Test
    @Disabled("в рандомный момент времени кладутся записи в таблицу trans_histrory")
    void getBalance_withTwoTransactions() {
        transactionService.createTransaction(createDto(1234567, 7654321, 100, 643));
        transactionService.createTransaction(createDto(1234567, 7654321, 500, 643));
        var balanceBeforeTrx =
                accountService.getBalanceByDate(
                        acctFrom.getAccountNumber(), LocalDate.now().minusDays(1));

        assertThat(balanceBeforeTrx).isEqualTo(1000.10);
    }

    @Test
    void notCreateTransaction_withoutMoney() {
        var trxDto = createDto(1234567, 7654321, 2000, 643);

        assertThatExceptionOfType(NotEnoughMoneyException.class)
                .isThrownBy(() -> transactionService.createTransaction(trxDto));

        assertThat(accountService.getAccount(1234567))
                .isNotNull()
                .hasFieldOrPropertyWithValue("balance", 1000.10);
        assertThat(accountService.getAccount(7654321))
                .isNotNull()
                .hasFieldOrPropertyWithValue("balance", 2000.00);
    }

    @Test
    void reverseTransaction() {
        var trans = transactionService.createTransaction(createDto(1234567, 7654321, 100, 643));
        var reverse = transactionService.reverseTransaction(trans.getId());

        assertThat(reverse).isNotNull();
        assertThat(accountService.getAccount(1234567))
                .isNotNull()
                .hasFieldOrPropertyWithValue("balance", 1000.10);
        assertThat(accountService.getAccount(7654321))
                .isNotNull()
                .hasFieldOrPropertyWithValue("balance", 2000.00);
    }

    @Test
    void notReverseTransaction_withoutOriginal() {
        assertThatExceptionOfType(InvalidDataException.class)
                .isThrownBy(() -> transactionService.reverseTransaction(-10));

        assertThat(accountService.getAccount(1234567))
                .isNotNull()
                .hasFieldOrPropertyWithValue("balance", 1000.10);
        assertThat(accountService.getAccount(7654321))
                .isNotNull()
                .hasFieldOrPropertyWithValue("balance", 2000.00);
    }

    @Test
    void addCard() {
        var account = accountService.addCard(acctFrom, CardProgram.GOLD);

        assertThat(account.getCards()).hasSize(1);
        assertThat(account.getCards().get(0).cardNumber()).hasSize(16);
        assertThat(account.getCards().get(0).cardNumber()).startsWith("48211212");
    }

    @Test
    void sendTrxLoggerEvent() {
        transactionService.createTransaction(createDto(1234567, 7654321, 100, 643));

        assertThat(
                        applicationEvents.stream(TrxLoggerEvent.class)
                                .filter(event -> event.message().equals("Transaction created!"))
                                .count())
                .isEqualTo(1);
    }

    @Test
    void showHistory() {
        transactionService.createTransaction(createDto(1234567, 7654321, 100, 643));
        transactionService.createTransaction(createDto(1234567, 7654321, 500, 643));
        var history = transactionService.getAllHistory();

        assertThat(history).hasSize(2);
    }

    @Test
    void showEmptyHistory() {
        var history = transactionService.getAllHistory();

        assertThat(history).isEmpty();
    }

    @Test
    void showHistoryWithAccount() {
        transactionService.createTransaction(createDto(1234567, 7654321, 100, 643));
        transactionService.createTransaction(createDto(7654321, 1234567, 500, 643));
        var history = transactionService.getHistory(1234567L, LocalDate.now().minusDays(1), LocalDate.now().plusDays(1));

        assertThat(history).hasSize(2);
    }

    @Test
    void notShowHistoryWithAccount_beforeTransaction() {
        transactionService.createTransaction(createDto(1234567, 7654321, 100, 643));
        var history = transactionService.getHistory(1234567L, LocalDate.now().minusDays(2), LocalDate.now().minusDays(1));

        assertThat(history).isEmpty();
    }
}
