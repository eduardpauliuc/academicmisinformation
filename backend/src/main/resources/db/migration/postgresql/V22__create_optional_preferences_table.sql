CREATE TABLE IF NOT EXISTS migrations.optional_preferences(
    student_id  INTEGER,
    course_id   INTEGER,
    rank        INTEGER,

    PRIMARY KEY (student_id, course_id),

    CONSTRAINT FK_optional_preferences_student_id FOREIGN KEY (student_id) REFERENCES migrations.students(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    CONSTRAINT FK_optional_preferences_course_id FOREIGN KEY (course_id) REFERENCES migrations.courses(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
)