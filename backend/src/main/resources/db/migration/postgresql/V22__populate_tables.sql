INSERT INTO migrations.faculties(name)
VALUES ('Faculty of Mathematics and Computer Science'),
       ('Faculty of Philosophy and History'),
       ('Faculty of Law');

INSERT INTO migrations.specializations(faculty_id, chief_of_department_id, degree_id, name, study_language,
                                       letter_identifier, semesters)
VALUES (1, NULL, 1, 'Computer Science', 'English', 'A', 6),
       (2, NULL, 2, 'History', 'Romanian', 'B', 4),
       (1, NULL, 1, 'Computer Science', 'German', 'C', 6),
       (3, NULL, 1, 'Civil Law', 'Romanian', 'D', 6);

INSERT INTO migrations.courses (specialization_id, name, credits, description, semester_number, is_optional,
                                maximum_students_number)
VALUES (4, 'Human Rights', 4, 'You will study human rights', 2, b'0', NULL),
       (1, 'Software Engineering', 6, 'You will study software engineering', 4, b'0', NULL),
       (3, 'Databases I', 6, 'You will study databases I', 3, b'0', NULL),
       (1, 'Advanced Problem Solving', 3, 'You will talk philosophy', 2, b'1', 200),
       (1, 'Cryptography', 3, 'You will encrypt stuff', 5, b'1', 120);
