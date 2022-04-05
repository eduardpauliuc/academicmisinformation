CREATE TABLE IF NOT EXISTS migrations.teachers
(
    id          SERIAL,
    account_id  INTEGER,
    title_id    INTEGER,

    PRIMARY KEY (id),

    CONSTRAINT FK_teachers_account_id FOREIGN KEY (account_id) REFERENCES migrations.accounts (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    CONSTRAINT FK_teachers_title_id FOREIGN KEY (title_id) REFERENCES migrations.titles (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
)