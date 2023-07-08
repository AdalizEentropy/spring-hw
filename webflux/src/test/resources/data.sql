INSERT INTO accounts (account_number, balance, currency) VALUES (12345, 1000, 643);
INSERT INTO accounts (account_number, balance, currency) VALUES (54321, 200, 643);

INSERT INTO transactions (transaction_time, account_from, account_to, amount, currency) VALUES ('2023-07-08 01:33:27', 12345, 54321, 110, 643);
INSERT INTO transactions (transaction_time, account_from, account_to, amount, currency) VALUES ('2023-07-08 01:33:27', 54321, 12345, 50, 643);