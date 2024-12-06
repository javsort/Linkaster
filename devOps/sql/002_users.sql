--
--  Title: 002_users.sql
--  Author: Ortega Mendoza, Javier & Gonzalez Fernandez, Marcos 
--  Date: 2024
--  Code Version: 1.0
--  Availability: https://github.com/javsort/Linkaster
--

-- Create the users table
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    role VARCHAR(255),
    public_key LONGTEXT,
    private_key LONGTEXT,
    FOREIGN KEY (role) REFERENCES roles(role) ON DELETE SET NULL
);

-- Insert an admin user
INSERT INTO users (first_name, last_name, password, email, role, public_key, private_key)
VALUES ('admin', 'lastname', 'admin_password', 'admin@example.com', 'ADMIN', NULL, NULL), 
       ('student','lastname', 'student_password', 'student@example.com', 'STUDENT', NULL, NULL),
       ('teacher','lastname', 'teacher_password', 'teacher@example.com', 'TEACHER', NULL, NULL),
       ('adminteacher','lastname', 'adminteacher_password', 'adminteacher@example.com', 'ADMINTEACHER', NULL, NULL),
       ('User One', '5', '$2a$10$daX7YxQjzrKU2E4cFZW/OewEzY3VDmJE6/4H2FhBepOfUuwYsmP2i', 'user1@lancaster.ac.uk', 'STUDENT', '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'),
       ('User Two', '6', '$2a$10$ZVYiZIk61WgGwbB6KjH9R.2EWVBMyqvAYxOGAzOeTQXh0CpQFklGu', 'user2@lancaster.ac.uk', 'STUDENT', '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'),
       ('User Three', '7', '$2a$10$UUS5qPNYl009vLz.Q6fn5OYWzcWDnadM/LGVOupq0abnAdrEJ7irW', 'user3@lancaster.ac.uk', 'STUDENT', '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'),
       ('User Four', '8', '$2a$10$DHim/ECEJvRyc195GgSrzudPf8vcd6j1Un7hZ07v8dMJRvMBB1fna', 'user4@lancaster.ac.uk', 'STUDENT', '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'),
       ('User Five', '9', '$2a$10$vcrz4G.v.2VcXCnWMcp1RejD4c/WbA4/KsUR52pYQVgyCRWEn4oS2', 'user5@lancaster.ac.uk', 'STUDENT', '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'),
       ('User Six', '10', '$2a$10$mAnfiPOMdoHtmgSuBrbVOexeskoeMbHE1iT/EopbvxVm/2LI.U8L2', 'user6@lancaster.ac.uk', 'STUDENT', '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'),
       ('teacher one', 'lastname', 'teacher1_password', 'teacher1@example.com', 'TEACHER', 'teacher1_public_key_123', 'teacher1_private_key_123'),
        ('teacher two', 'lastname', 'teacher2_password', 'teacher2@example.com', 'TEACHER', 'teacher2_public_key_456', 'teacher2_private_key_456');

CREATE TABLE students (
    id BIGINT PRIMARY KEY, -- This matches the "id" in the users table
    student_id VARCHAR(255) UNIQUE,
    course VARCHAR(255),
    year VARCHAR(255),
    enrolled_modules_ids JSON,
    FOREIGN KEY (id) REFERENCES users(id) ON DELETE CASCADE
);

INSERT INTO students (id, student_id, course, year, enrolled_modules_ids)
VALUES (2, '123456', 'Computer Science', "3", NULL),
        (5, '123444', 'Computer Science', "3", NULL),
        (6, '123423', 'Computer Science', "3", NULL),
        (7, '123412', 'Computer Science', "3", NULL),
        (8, '132244', 'Computer Science', "3", NULL),
        (9, '121455', 'Computer Science', "3", NULL),
        (10, '122343', 'Computer Science', "3", NULL);


CREATE TABLE teachers (
    id BIGINT PRIMARY KEY, -- This matches the "id" in the users table
    subject VARCHAR(255),
    teaching_modules_ids JSON,
    FOREIGN KEY (id) REFERENCES users(id) ON DELETE CASCADE
);

INSERT INTO teachers (id, subject, teaching_modules_ids)
VALUES  (3, "Digital Systems", NULL),
        (11, "Digital Systems", NULL),
        (12, "Digital Systems", NULL);


-- Current modules addition (NOT FINAL -> missing to join the services)
-- CREATE TABLE teacher_user_modules (
--    teacher_user_id BIGINT NOT NULL, -- Foreign key to teachers table
--    module_name VARCHAR(255) NOT NULL, -- Individual module name
--    PRIMARY KEY (teacher_user_id, module_name),
--    FOREIGN KEY (teacher_user_id) REFERENCES teachers(id) ON DELETE CASCADE
-- );

-- Current modules addition (NOT FINAL -> missing to join the services)
-- CREATE TABLE student_user_registered_modules  (
--    student_user_id BIGINT NOT NULL, -- Foreign key to teachers table
--    module_name VARCHAR(255) NOT NULL, -- Individual module name
--    PRIMARY KEY (student_user_id, module_name),
--    FOREIGN KEY (student_user_id) REFERENCES students(id) ON DELETE CASCADE
-- );