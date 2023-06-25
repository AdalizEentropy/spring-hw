package ru.nspk.util;

import ru.nspk.account.model.Account;

public class CreateTestAccounts {
    public static Account createAccountSender() {
        return new Account(1234567, 1000.10, 643, true);
    }

    public static Account createAccountReceiver() {
        return new Account(7654321, 2000.00, 643, true);
    }
}
