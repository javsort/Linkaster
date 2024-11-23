-- Create the users table
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    role VARCHAR(255),
    key_pair BLOB,
    FOREIGN KEY (role) REFERENCES roles(role) ON DELETE SET NULL
);

-- Insert an admin user
INSERT INTO users (first_name, last_name, password, email, role, key_pair)
VALUES ('admin', 'lastname', 'admin_password', 'admin@example.com', 'ADMIN', NULL), 
       ('student','lastname', 'student_password', 'student@example.com', 'STUDENT', NULL),
       ('teacher','lastname', 'teacher_password', 'teacher@example.com', 'TEACHER', NULL),
       ('adminteacher','lastname', 'adminteacher_password', 'adminteacher@example.com', 'ADMINTEACHER', NULL);

