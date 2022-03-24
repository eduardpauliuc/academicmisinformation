CREATE TABLE IF NOT EXISTS migrations.accounts
(
    id              SERIAL,
    username        TEXT NOT NULL UNIQUE,
    email           TEXT NOT NULL UNIQUE,
    password_digest TEXT NOT NULL,
    role_id         INTEGER,
    first_name      TEXT,
    last_name       TEXT,
    birth_date      DATE,

    PRIMARY KEY (id),
    CONSTRAINT FK_accounts_role_id FOREIGN KEY (role_id) REFERENCES migrations.roles (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
)