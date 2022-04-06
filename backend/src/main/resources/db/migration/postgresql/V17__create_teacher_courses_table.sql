CREATE TABLE IF NOT EXISTS migrations.teacher_courses
(
    teacher_id  INTEGER,
    course_id   INTEGER,

    PRIMARY KEY (teacher_id, course_id),

    CONSTRAINT FK_teacher_courses_teacher_id FOREIGN KEY (teacher_id) REFERENCES migrations.teachers(account_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    CONSTRAINT FK_teacher_courses_course_id FOREIGN KEY (course_id) REFERENCES migrations.courses(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
)