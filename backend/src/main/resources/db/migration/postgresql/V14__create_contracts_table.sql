CREATE TABLE IF NOT EXISTS migrations.contracts
(
    id                SERIAL,
    student_id        INTEGER,
    specialization_id INTEGER,
    start_date        DATE NOT NULL,
    end_date          DATE NOT NULL,
    semester_number   INTEGER,
    group_code        TEXT,

    PRIMARY KEY (id),

    CONSTRAINT FK_contracts_student_id FOREIGN KEY (student_id) REFERENCES migrations.students (account_id)
        ON DELETE SET NULL
        ON UPDATE CASCADE,

    CONSTRAINT FK_contracts_specialization_id FOREIGN KEY (specialization_id) REFERENCES migrations.specializations (id)
        ON DELETE SET NULL
)