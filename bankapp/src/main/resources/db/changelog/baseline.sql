--liquibase formatted sql
--changeset bankapp_sql:initial_db

CREATE TABLE IF NOT EXISTS accounts
(
    account_number bigint NOT NULL PRIMARY KEY,
    balance double NOT NULL,
    currency integer NOT NULL
    );

CREATE TABLE IF NOT EXISTS transactions
(
    id bigint NOT NULL PRIMARY KEY,
    transaction_time timestamp NOT NULL,
    account_from bigint NOT NULL,
    account_to bigint NOT NULL,
    amount double NOT NULL,
    currency integer NOT NULL,
    CONSTRAINT fk_transactions_to_acct_from FOREIGN KEY(account_from) REFERENCES accounts(account_number),
    CONSTRAINT fk_transactions_to_acct_to FOREIGN KEY(account_to) REFERENCES accounts(account_number)
    );

CREATE TABLE IF NOT EXISTS balance_history
(
    id bigint NOT NULL PRIMARY KEY AUTO_INCREMENT,
    account_number bigint NOT NULL,
    history_time timestamp NOT NULL,
    balance double NOT NULL,
    CONSTRAINT fk_balance_history_to_acct FOREIGN KEY(account_number) REFERENCES accounts(account_number)
);