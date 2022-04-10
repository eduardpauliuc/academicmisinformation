CREATE TABLE IF NOT EXISTS migrations.courses
(
    id                      SERIAL,
    specialization_id       INTEGER,
    name                    TEXT    NOT NULL,
    credits                 INTEGER NOT NULL,
    description             TEXT,
    semester_number         INTEGER NOT NULL,
    is_optional             BIT     NOT NULL,
    maximum_students_number INTEGER,

    PRIMARY KEY (id),

    CONSTRAINT FK_courses_specialization_id FOREIGN KEY (specialization_id) REFERENCES migrations.specializations (id)
        ON DELETE SET NULL
        ON UPDATE CASCADE
)