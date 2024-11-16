-- Create the users table
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    role_id BIGINT,
    keyPair BLOB,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE SET NULL
);

-- Insert an admin user
INSERT INTO users (username, password, email, role_id, keyPair)
VALUES ('admin', 'admin_password', 'admin@example.com', 1, NULL), 
       ('student', 'student_password', 'student@example.com', 2, NULL),
       ('teacher', 'teacher_password', 'teacher@example.com', 3, NULL),
       ('adminteacher', 'adminteacher_password', 'adminteacher@example.com', 4, NULL);

