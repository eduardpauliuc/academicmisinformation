INSERT INTO migrations.accounts(role_id, username, email, password_digest, first_name, last_name, birth_date)
VALUES (1, 'student1', 'student1@gmail.com', '$2a$10$XrXk3141KVPvidKP3bcMVeQlC5kUVR99Z1inyuhpecfHV2wDj2v0a', 'S1FN',
        'S1LN', '2000-02-02'),
       (5, 'chief1', 'chief1@gmail.com', '$2a$10$uJBdtMP8X7SKM1ZXuGxJ2ux5jALIa/I0QO/FDFfFqVFAfaXyV0UK.', 'C1FN',
        'C1LN', '1983-05-23'),
       (4, 'admin', 'admin@gmail', '$2a$10$3y1S3zEco/.lzOeGl..JU.fMEwpCwaYwK4SBkrOEAEy5YCT8Hhcz.', 'admin', 'admin',
        '1990-04-12'),
       (1, 'student2', 'student2@gmail.com', '$2a$10$XrXk3141KVPvidKP3bcMVeQlC5kUVR99Z1inyuhpecfHV2wDj2v0a', 'S2FN',
        'S2LN', '2001-03-15'),
       (3, 'staff1', 'staff1@gmail.com', '$2a$10$skUnlQIeK4bp1f6glb/uL.0LnODi94goheEiRl64MmUxLkjhkK1FC', 'STAFF1FN',
        'STAFF1LN', '1970-04-05');


INSERT INTO migrations.faculties(name)
VALUES ('Faculty of Mathematics and Computer Science'),
       ('Faculty of Philosophy and History'),
       ('Faculty of Law');


INSERT INTO migrations.students(account_id, registration_number)
VALUES (1, 'AAAAAAAAA1'),
       (4, 'BBBBBBBBB2');


INSERT INTO migrations.staff_members(account_id, faculty_id)
VALUES (5, 1);


INSERT INTO migrations.teachers(account_id, title_id)
VALUES (2, 4); -- teacher (and chief)


INSERT INTO migrations.specializations(faculty_id, chief_of_department_id, degree_id, name, study_language,
                                       letter_identifier, semesters)
VALUES (1, 2, 1, 'Computer Science', 'English', 'A', 6), -- teacher with id 2 is chief of department
       (2, NULL, 2, 'History', 'Romanian', 'B', 4),
       (1, NULL, 1, 'Computer Science', 'German', 'C', 6),
       (3, NULL, 1, 'Civil Law', 'Romanian', 'D', 6);


INSERT INTO migrations.courses (specialization_id, name, credits, description, semester_number, is_optional,
                                maximum_students_number)
VALUES (4, 'Human Rights', 4, 'You will study human rights', 2, false, NULL),
       (1, 'Software Engineering', 6, 'You will study software engineering', 4, false, NULL),
       (3, 'Databases I', 6, 'You will study databases I', 3, false, NULL),
       (1, 'Advanced Problem Solving', 3, 'You will talk philosophy', 2, true, 200),
       (1, 'Cryptography', 3, 'You will encrypt stuff', 5, true, 120);


INSERT INTO migrations.contracts(student_id, specialization_id, start_date, end_date, semester_number, group_code)
VALUES (1, 1, '2021-10-01', '2022-02-15', 1, 'A117'), -- group_code = specialization.letter_identifier + '...'
       (1, 1, '2022-02-16', '2022-07-01', 2, 'A127'),
       (4, 1, '2021-10-01', '2022-02-15', 5, 'A315');


INSERT INTO migrations.optional_preferences(student_id, course_id, rank)
VALUES (1, 4, 1), -- student with id 1 has ranked course with id 4 as #1 preference
       (1, 5, 2), -- student with id 1 has ranked course with id 5 as #2 preference
       (4, 4, 1),
       (4, 5, 2);


INSERT INTO migrations.teacher_courses(teacher_id, course_id)
VALUES (2, 2), -- teacher with id 2 teaches Software Engineering (course with id 2)
       (2, 4), -- teacher with id 2 teaches Advanced Problem Solving (course with id 4)
       (2, 5); -- teacher with id 2 teaches Cryptography (course with id 5)


INSERT INTO migrations.optional_proposals(specialization_id, teacher_id, status_id, comments, maximum_students_number,
                                          credits, description, semester_number, name)
VALUES (1, 2, 2, NULL, 125, 4, 'You will study Deep Learning', 6, 'Deep Learning Optional'),
       (1, 2, 2, NULL, 50, 3, 'You will study VR', 5, 'VR Optional');
