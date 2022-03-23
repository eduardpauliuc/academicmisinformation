CREATE TABLE IF NOT EXISTS migrations.administrators
(
    id         SERIAL,
    account_id INTEGER,

    PRIMARY KEY (id),
    CONSTRAINT FK_administrators_account_id FOREIGN KEY (account_id) REFERENCES migrations.accounts (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
)