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
    id bigint NOT NULL PRIMARY KEY AUTO_INCREMENT,
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

CREATE TABLE IF NOT EXISTS users
(
    username varchar(50) NOT NULL PRIMARY KEY,
    password varchar(500) NOT NULL,
    enabled boolean NOT NULL
);

CREATE TABLE IF NOT EXISTS authorities
(
    username varchar(50) NOT NULL,
    authority varchar(50) NOT NULL,
    CONSTRAINT fk_authorities_users FOREIGN KEY(username) REFERENCES users(username),
    CONSTRAINT uq_username_authority UNIQUE (username, authority)
);

CREATE TABLE IF NOT EXISTS transaction_history
(
    id bigint NOT NULL PRIMARY KEY AUTO_INCREMENT,
    transaction_id bigint NOT NULL,
    history_time timestamp NOT NULL,
    CONSTRAINT fk_trx_history_trx FOREIGN KEY(transaction_id) REFERENCES transactions(id)
);