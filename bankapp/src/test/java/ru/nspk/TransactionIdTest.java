package ru.nspk;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.nspk.util.CreateTestAccounts.createAccountReceiver;
import static ru.nspk.util.CreateTestAccounts.createAccountSender;
import static ru.nspk.util.CreateTestTransactions.createDto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import ru.nspk.account.model.Account;
import ru.nspk.account.service.AccountService;
import ru.nspk.transaction.TransactionProperties;
import ru.nspk.transaction.service.TransactionService;

@SpringBootTest(properties = "spring.main.allow-bean-definition-overriding=true")
@ActiveProfiles("prod")
@ContextConfiguration(classes = TestAppConfig.class)
@AutoConfigureTestDatabase
class TransactionIdTest {
    @Autowired AccountService accountService;
    @Autowired TransactionService transactionService;
    @Autowired TransactionProperties properties;
    Account acctFrom = createAccountSender();
    Account acctTo = createAccountReceiver();

    @BeforeEach
    void createAccount() {
        accountService.createAccount(acctFrom);
        accountService.createAccount(acctTo);
    }

    @Test
    void getBalance_withTransaction() {
        var trans = transactionService.createTransaction(createDto(1234567, 7654321, 100, 643));

        assertThat(trans).isNotNull();
        assertThat(String.valueOf(trans.getId()))
                .hasSize(properties.getTrxIdLength())
                .hasSize(10)
                .startsWith("30910823");
    }
}
