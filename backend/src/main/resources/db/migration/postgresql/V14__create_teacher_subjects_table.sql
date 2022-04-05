CREATE TABLE IF NOT EXISTS migrations.teacher_subjects(
    teacher_id INTEGER,
    subject_id INTEGER,

    PRIMARY KEY(teacher_id, subject_id),
    CONSTRAINT FK_teacher_subjects_teacher_id FOREIGN KEY (teacher_id) REFERENCES migrations.teachers(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    CONSTRAINT FK_teacher_subjects_subject_id FOREIGN KEY (subject_id) REFERENCES migrations.subjects(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
)