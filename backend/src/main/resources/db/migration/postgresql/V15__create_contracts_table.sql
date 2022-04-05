CREATE TABLE IF NOT EXISTS migrations.contracts
(
    id                  SERIAL,
    student_id          INTEGER,
    specialization_id   INTEGER,
    start_date          DATE,
    end_date            DATE,
    tax                 INTEGER,
    semester_number     INTEGER,
    group_number        INTEGER,

    PRIMARY KEY (id),

    CONSTRAINT FK_contracts_student_id FOREIGN KEY (student_id) REFERENCES migrations.students(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    CONSTRAINT FK_contracts_specialization_id FOREIGN KEY (specialization_id) REFERENCES migrations.specializations(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
)