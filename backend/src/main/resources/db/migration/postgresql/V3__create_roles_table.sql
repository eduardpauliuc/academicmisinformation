CREATE TABLE IF NOT EXISTS migrations.roles
(
    id   SERIAL,
    name TEXT NOT NULL,

    PRIMARY KEY (id)
)