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

CREATE TABLE students (
    id BIGINT PRIMARY KEY, -- This matches the "id" in the users table
    student_id VARCHAR(255) UNIQUE,
    course VARCHAR(255),
    year INT,
    modules JSON,
    FOREIGN KEY (id) REFERENCES users(id) ON DELETE CASCADE
);

INSERT INTO students (id, student_id, course, year, modules)
VALUES (2, '123456', 'Computer Science', 1, NULL);


CREATE TABLE teachers (
    id BIGINT PRIMARY KEY, -- This matches the "id" in the users table
    subject VARCHAR(255),
    modules JSON,
    FOREIGN KEY (id) REFERENCES users(id) ON DELETE CASCADE
);

INSERT INTO teachers (id, subject, modules)
VALUES (3, "Digital Systems", NULL);


-- Current modules addition (NOT FINAL -> missing to join the services)
CREATE TABLE teacher_user_modules (
    teacher_user_id BIGINT NOT NULL, -- Foreign key to teachers table
    module_name VARCHAR(255) NOT NULL, -- Individual module name
    PRIMARY KEY (teacher_user_id, module_name),
    FOREIGN KEY (teacher_user_id) REFERENCES teachers(id) ON DELETE CASCADE
);

-- Current modules addition (NOT FINAL -> missing to join the services)
CREATE TABLE student_user_registered_modules  (
    student_user_id BIGINT NOT NULL, -- Foreign key to teachers table
    module_name VARCHAR(255) NOT NULL, -- Individual module name
    PRIMARY KEY (student_user_id, module_name),
    FOREIGN KEY (student_user_id) REFERENCES students(id) ON DELETE CASCADE
);