CREATE TABLE IF NOT EXISTS migrations.accounts
(
    id              SERIAL,
    username        TEXT NOT NULL,
    email           TEXT NOT NULL,
    password_digest TEXT NOT NULL,
    type            TEXT NOT NULL,
    first_name      TEXT,
    last_name       TEXT,
    birth_date      DATE,

    PRIMARY KEY (id)
)