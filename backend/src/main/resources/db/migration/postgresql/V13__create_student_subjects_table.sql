CREATE TABLE IF NOT EXISTS migrations.student_subjects(
    student_id INTEGER,
    subject_id INTEGER,
    grade INTEGER,

    PRIMARY KEY(student_id, subject_id),
    CONSTRAINT FK_student_subjects_student_id FOREIGN KEY(student_id) REFERENCES migrations.students(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    CONSTRAINT FK_student_subjects_subject_id FOREIGN KEY(subject_id) REFERENCES migrations.subjects(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
)