CREATE TABLE IF NOT EXISTS migrations.students
(
    account_id          INTEGER,
    registration_number TEXT UNIQUE,

    PRIMARY KEY (account_id),

    CONSTRAINT FK_students_account_id FOREIGN KEY (account_id) REFERENCES migrations.accounts (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
)