CREATE TABLE IF NOT EXISTS migrations.subjects(
    id SERIAL,
    specialization_id INTEGER,
    name TEXT,
    credits INTEGER,
    description TEXT,
    semester_number INTEGER,
    is_optional BIT,
    minimum_followers_required INTEGER,

    PRIMARY KEY(id),
    CONSTRAINT FK_subjects_specialization_id FOREIGN KEY (specialization_id) REFERENCES migrations.specializations(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
)