CREATE TABLE IF NOT EXISTS migrations.students
(
    id SERIAL,
    account_id INTEGER,

    PRIMARY KEY (id),
    CONSTRAINT FK_students_account_id FOREIGN KEY (account_id) REFERENCES migrations.accounts (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
)