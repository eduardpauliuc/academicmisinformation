CREATE TABLE IF NOT EXISTS migrations.active_contracts(
    student_id INTEGER,
    specialization_id INTEGER,
    start_date DATE,
    end_date DATE,
    tax INTEGER,

    PRIMARY KEY(student_id, specialization_id),
    CONSTRAINT FK_active_contract_student_id FOREIGN KEY (student_id) REFERENCES migrations.students(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    CONSTRAINT FK_active_contract_specialization_id FOREIGN KEY (specialization_id) REFERENCES migrations.specializations(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
)