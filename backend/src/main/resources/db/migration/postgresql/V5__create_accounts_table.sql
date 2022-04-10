CREATE TABLE IF NOT EXISTS migrations.accounts
(
    id              SERIAL,
    role_id         INTEGER NOT NULL,
    username        TEXT    NOT NULL UNIQUE,
    email           TEXT    NOT NULL UNIQUE,
    password_digest TEXT    NOT NULL,
    first_name      TEXT,
    last_name       TEXT,
    birth_date      DATE,

    PRIMARY KEY (id),

    CONSTRAINT FK_accounts_role_id FOREIGN KEY (role_id) REFERENCES migrations.roles (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
)