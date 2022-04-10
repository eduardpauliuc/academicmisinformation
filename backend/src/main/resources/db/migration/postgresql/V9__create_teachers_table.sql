CREATE TABLE IF NOT EXISTS migrations.teachers
(
    account_id INTEGER,
    title_id   INTEGER,

    PRIMARY KEY (account_id),

    CONSTRAINT FK_teachers_account_id FOREIGN KEY (account_id) REFERENCES migrations.accounts (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    CONSTRAINT FK_teachers_title_id FOREIGN KEY (title_id) REFERENCES migrations.titles (id)
        ON DELETE SET NULL
        ON UPDATE CASCADE
)