CREATE TABLE IF NOT EXISTS migrations.statuses
(
    id   SERIAL,
    name TEXT NOT NULL,

    PRIMARY KEY (id)
)