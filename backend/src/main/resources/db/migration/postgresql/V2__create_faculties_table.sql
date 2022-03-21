CREATE TABLE IF NOT EXISTS migrations.faculties
(
    id      SERIAL,
    name    VARCHAR(256) NOT NULL,
    website TEXT,

    PRIMARY KEY (id)
);