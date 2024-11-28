-- Create the users table
CREATE TABLE modules (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    module_owner_id BIGINT,
    module_name VARCHAR(255) NOT NULL,
    module_code VARCHAR(255) UNIQUE,
    student_enrolled_ids JSON,
    teacher_enrolled_ids JSON,
    events JSON,
    module_type VARCHAR(255),
    FOREIGN KEY (module_owner_id) REFERENCES users(id) ON DELETE SET NULL
);

-- Instert a module class and a module club
INSERT INTO modules (module_owner_id, module_name, module_code, student_enrolled_ids, teacher_enrolled_ids, events, module_type)
VALUES (3, 'Internet Application of Engineering', "123456", NULL, NULL, NULL, 'class'),
       (3, 'Robotics', 123457, NULL, NULL, NULL, 'club');


-- Crate class table
CREATE TABLE classes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY, -- This matches the "id" in the modules table
    class_name VARCHAR(255) NOT NULL,
    class_owner_id BIGINT NOT NULL,
    class_teacher_ids JSON,
    class_student_ids JSON,
    class_events JSON,

    FOREIGN KEY (id) REFERENCES modules(id) ON DELETE CASCADE
    );       

-- Insert a class
INSERT INTO classes (class_name, class_owner_id, class_teacher_ids, class_student_ids, class_events)
VALUES ('Internet Application of Engineering', 3, NULL, NULL, NULL);

-- Crate club table
CREATE TABLE clubs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY, -- This matches the "id" in the modules table
    club_name VARCHAR(255) NOT NULL,
    club_owner_id BIGINT NOT NULL,
    club_student_ids JSON,
    club_events JSON,

    FOREIGN KEY (id) REFERENCES modules(id) ON DELETE CASCADE
    );

-- Insert a club
INSERT INTO clubs (club_name, club_owner_id, club_student_ids, club_events)
VALUES ('Robotics', 2, NULL, NULL);

