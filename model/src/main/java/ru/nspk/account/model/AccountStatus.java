package ru.nspk.account.model;

import java.util.List;
import java.util.Random;

public enum AccountStatus {
    OPEN,
    CLOSED;

    private static final List<AccountStatus> VALUES = List.of(values());
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static AccountStatus randomAccountStatus() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
