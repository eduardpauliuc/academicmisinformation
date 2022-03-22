CREATE TABLE IF NOT EXISTS migrations.faculties
(
    id      SERIAL,
    name    TEXT NOT NULL,
    website TEXT,

    PRIMARY KEY (id)
);