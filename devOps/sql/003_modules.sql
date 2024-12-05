-- Create the modules table
CREATE TABLE modules (
    module_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    module_owner_id BIGINT NOT NULL,
    module_owner_name VARCHAR(255) NOT NULL,
    module_owner_type VARCHAR(255) NOT NULL,
    module_name VARCHAR(255) NOT NULL UNIQUE,
    module_code VARCHAR(255) UNIQUE NOT NULL,
    module_type VARCHAR(255),
    UNIQUE KEY(module_code) -- Ensure module codes are unique
);

-- Create the module_student_list table
CREATE TABLE module_student_list (
    module_id BIGINT NOT NULL,
    student_id BIGINT NOT NULL,
    PRIMARY KEY (module_id, student_id),
    FOREIGN KEY (module_id) REFERENCES modules (module_id) ON DELETE CASCADE
);

-- Insert modules (class and club modules)
INSERT INTO modules (module_owner_id, module_owner_name, module_owner_type, module_name, module_code, module_type)
VALUES 
    (11, 'John Doe', 'TEACHER', 'Internet Application of Engineering', '123456', 'class_module'),
    (2, 'Jane Smith', 'TEACHER', 'Robotics', '123457', 'club_module'),
    (3, 'Alice Brown', 'TEACHER', 'Artificial Intelligence', '123458', 'class_module'),
    (5, 'Bob Green', 'TEACHER', 'Machine Learning', '123459', 'club_module'),
    (6, 'Eve White', 'TEACHER', 'Deep Learning', '123460', 'club_module');

-- Insert student enrollments into the module_student_list table
INSERT INTO module_student_list (module_id, student_id)
VALUES
    (1, 2), -- Student 2 enrolled in Module 1
    (2, 2), -- Student 2 enrolled in Module 2
    (3, 3), -- Student 3 enrolled in Module 3
    (4, 3), -- Student 3 enrolled in Module 4
    (5, 3); -- Student 3 enrolled in Module 5
