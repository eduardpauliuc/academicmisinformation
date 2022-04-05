CREATE TABLE IF NOT EXISTS migrations.teachers(
    id SERIAL,
    account_id INTEGER,
    title TEXT,

    PRIMARY KEY (id),
    CONSTRAINT FK_teachers_account_id FOREIGN KEY (account_id) REFERENCES migrations.accounts (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
)