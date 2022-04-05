CREATE TABLE IF NOT EXISTS migrations.specializations(
    id SERIAL,
    faculty_id INTEGER,
    name TEXT,
    study_language TEXT,
    chief_of_department_id INTEGER,

    PRIMARY KEY (id),
    CONSTRAINT FK_specializations_faculty_id FOREIGN KEY (faculty_id) REFERENCES migrations.faculties (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    CONSTRAINT FK_specializations_chief_of_department_id FOREIGN KEY (chief_of_department_id) REFERENCES migrations.teachers (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
    )