-- Create the users table
CREATE TABLE modules (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    module_owner_id BIGINT NOT NULL,
    module_name VARCHAR(255) NOT NULL,
    module_code INTEGER  NOT NULL UNIQUE,
    student_enrolled_ids JSON,
    teacher_enrolled_ids JSON,
    events JSON,
    module_type VARCHAR(255),
    FOREIGN KEY (module_owner_id) REFERENCES users(teacher_user_id) ON DELETE SET NULL
);

--Instert a module class and a module club
INSERT INTO modules (module_owner_id, module_name, module_code, student_enrolled_ids, teacher_enrolled_ids, events, module_type)
VALUES (3, 'Internet Application of Engineering', 123456, NULL, NULL, NULL, 'class'),
       (3, 'Robotics', 123457, NULL, NULL, NULL, 'club');


-- Crate class table
CREATE TABLE classes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY, -- This matches the "id" in the modules table
    class_name VARCHAR(255) NOT NULL,
    class_code INTEGER NOT NULL UNIQUE,
    class_owner_id BIGINT NOT NULL,
    class_teacher_ids JSON,
    class_student_ids JSON,
    class_events JSON,

    FOREIGN KEY (id) REFERENCES modules(id) ON DELETE CASCADE
    );       

-- Insert a class
INSERT INTO classes (class_name, class_code, class_owner_id, class_teacher_ids, class_student_ids, class_events)
VALUES ('Internet Application of Engineering', 123456, 3, NULL, NULL, NULL);

-- Crate club table
CREATE TABLE clubs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY, -- This matches the "id" in the modules table
    club_name VARCHAR(255) NOT NULL,
    club_code INTEGER NOT NULL UNIQUE,
    club_owner_id BIGINT NOT NULL,
    club_student_ids JSON,
    club_events JSON,

    FOREIGN KEY (id) REFERENCES modules(id) ON DELETE CASCADE
    );

-- Insert a club
INSERT INTO clubs (club_name, club_code, club_owner_id, club_student_ids, club_events)
VALUES ('Robotics', 123457, 2, NULL, NULL);

-- Create the events table
CREATE TABLE events (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    event_name VARCHAR(255) NOT NULL,
    event_date DATE NOT NULL,
    event_start_time TIME NOT NULL,
    event_end_time TIME NOT NULL,
    event_location VARCHAR(255) NOT NULL,
    event_owner_id BIGINT NOT NULL,
    event_module_id BIGINT NOT NULL,
    event_type VARCHAR(255) NOT NULL,
    FOREIGN KEY (event_owner_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (event_module_id) REFERENCES modules(id) ON DELETE CASCADE
);

-- Insert data of events
INSERT INTO events (event_name, event_date, event_start_time, event_end_time, event_location, event_owner_id, event_module_id, event_type)
VALUES ('Internet Application of Engineering Lecture', '2024-12-03', '09:00:00', '11:00:00', '716', 3, 123456, 'class'),
       ('Internet Application of Engineering Workshop', '2024-12-06', '09:00:00', '11:00:00', '716', 3, 123456, 'class'),
       ('Robotics Club Meeting', '2024-12-04', '13:00:00', '15:00:00', 'Club Room 1', 2, 123457, 'club');