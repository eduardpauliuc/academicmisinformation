CREATE TABLE IF NOT EXISTS migrations.courses(
    id                      SERIAL,
    specialization_id       INTEGER,
    name                    TEXT,
    credits                 INTEGER,
    description             TEXT,
    semester_number         INTEGER,
    is_optional             BIT,
    maximum_students_number INTEGER,

    PRIMARY KEY (id),

    CONSTRAINT FK_courses_specialization_id FOREIGN KEY (specialization_id) REFERENCES migrations.specializations(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
)