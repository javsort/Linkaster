-- Create the modules table
CREATE TABLE modules (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    module_owner_id BIGINT,
    module_name VARCHAR(255) NOT NULL,
    module_code VARCHAR(255) UNIQUE,
    student_enrolled_ids JSON,
    module_type VARCHAR(255)
);

-- Insert a module class and a module club
INSERT INTO modules (module_owner_id, module_name, module_code, student_enrolled_ids, module_type)
VALUES 
    (2, 'Internet Application of Engineering', '123456', '[2]', 'class'),
    (2, 'Robotics', '123457', '[2]', 'club');

-- Create class table
CREATE TABLE class_modules (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    class_module_id BIGINT, -- This references the "id" in the modules table
    class_name VARCHAR(255) NOT NULL,
    class_owner_id BIGINT NOT NULL,
    class_teacher_ids JSON,
    FOREIGN KEY (class_module_id) REFERENCES modules(id) ON DELETE CASCADE
);       

-- Insert a class
INSERT INTO class_modules (class_module_id, class_name, class_owner_id, class_teacher_ids)
VALUES (1, 'Internet Application of Engineering', 3, '[3]');

-- Create club table
CREATE TABLE club_modules (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    club_module_id BIGINT, -- This references the "id" in the modules table
    club_name VARCHAR(255) NOT NULL,
    club_owner_id BIGINT NOT NULL,
    FOREIGN KEY (club_module_id) REFERENCES modules(id) ON DELETE CASCADE
);

-- Insert a club
INSERT INTO club_modules (club_module_id, club_name, club_owner_id)
VALUES (2, 'Robotics', 2);
