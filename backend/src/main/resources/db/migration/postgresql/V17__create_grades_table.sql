CREATE TABLE IF NOT EXISTS migrations.grades
(
    student_id  INTEGER,
    course_id   INTEGER,
    grade       INTEGER,

    PRIMARY KEY (student_id, course_id),

    CONSTRAINT FK_grades_student_id FOREIGN KEY (student_id) REFERENCES migrations.students(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    CONSTRAINT FK_grades_course_id FOREIGN KEY (course_id) REFERENCES migrations.courses(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
)