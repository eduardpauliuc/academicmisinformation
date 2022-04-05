CREATE TABLE IF NOT EXISTS migrations.optional_proposals
(
    id                      SERIAL,
    specialization_id       INTEGER,
    teacher_id              INTEGER,
    status_id               INTEGER,
    comments                TEXT,
    maximum_students_number INTEGER,
    credits                 INTEGER,
    description             TEXT,
    semester_number         INTEGER,
    name                    TEXT,

    PRIMARY KEY(id),

    CONSTRAINT FK_optional_proposals_specialization_id FOREIGN KEY (specialization_id) REFERENCES migrations.specializations(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    CONSTRAINT FK_optional_proposals_teacher_id FOREIGN KEY (teacher_id) REFERENCES migrations.teachers(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    CONSTRAINT FK_optional_proposals_status_id FOREIGN KEY (status_id) REFERENCES migrations.statuses(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
)