-- Create the modules table
CREATE TABLE modules (
    module_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    module_owner_id BIGINT,
    module_name VARCHAR(255) NOT NULL,
    module_code VARCHAR(255) UNIQUE,
    student_enrolled_ids JSON,
    module_type VARCHAR(255)
);

-- Insert a module class and a module club
INSERT INTO modules (module_owner_id, module_name, module_code, student_enrolled_ids, module_type)
VALUES 
    (11, 'Internet Application of Engineering', '123456', '[2]', 'class'),
    (2, 'Robotics', '123457', '[2]', 'club'),
    (3, 'Artificial Intelligence', '123458', '[3]', 'class'),
    (5, 'Machine Learning', '123459', '[3]', 'club'),
    (6, 'Deep Learning', '123460', '[3]', 'club');

