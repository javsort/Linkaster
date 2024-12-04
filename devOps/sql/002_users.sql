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
       ('User One', '5', 'user1_password', 'user1@example.com', 'STUDENT', 'user1_public_key_123', 'user1_private_key_123'),
       ('User Two', '6', 'user2_password', 'user2@example.com', 'STUDENT', 'user2_public_key_456', 'user2_private_key_456'),
       ('User Three', '7', 'user3_password', 'user3@example.com', 'STUDENT', 'user3_public_key_789', 'user3_private_key_789'),
       ('User Four', '8', 'user4_password', 'user4@example.com', 'STUDENT', 'user4_public_key_101112', 'user4_private_key_101112'),
       ('User Five', '9', 'user5_password', 'user5@example.com', 'STUDENT', 'user5_public_key_131415', 'user5_private_key_131415'),
       ('User Six', '10', 'user6_password', 'user6@example.com', 'STUDENT', 'user6_public_key_161718', 'user6_private_key_161718'),
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