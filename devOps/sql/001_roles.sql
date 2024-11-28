-- Create the roles table
CREATE TABLE roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    role VARCHAR(255) NOT NULL UNIQUE,
    description VARCHAR(255)
);

-- Insert roles
INSERT INTO roles (id, role, description) VALUES
(1, 'ADMIN', 'Administrator role with full access'),
(2, 'STUDENT', 'Student role with limited access'),
(3, 'TEACHER', 'Teacher role with academic access'),
(4, 'ADMINTEACHER', 'Combined admin and teacher role');
