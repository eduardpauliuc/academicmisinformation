CREATE TABLE IF NOT EXISTS migrations.grades
(
    id         SERIAL,
    student_id INTEGER,
    course_id  INTEGER NOT NULL,
    grade      INTEGER,

    PRIMARY KEY (id),

    CONSTRAINT FK_grades_student_id FOREIGN KEY (student_id) REFERENCES migrations.students (account_id)
        ON DELETE SET NULL
        ON UPDATE CASCADE,

    CONSTRAINT FK_grades_course_id FOREIGN KEY (course_id) REFERENCES migrations.courses (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
)