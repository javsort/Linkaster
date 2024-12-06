--
--  Title: 004_events.sql
--  Author: Ortega Mendoza, Javier & Gonzalez Fernandez, Marcos 
--  Date: 2024
--  Code Version: 1.0
--  Availability: https://github.com/javsort/Linkaster
--

-- Create the timetables table
CREATE TABLE timetables (
    timetable_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_owner_id BIGINT NOT NULL
);

-- Insert data into the timetables table
INSERT INTO timetables (user_owner_id)
VALUES
(1),  -- Timetable for User 1
(2),  -- Timetable for User 2
(3),  -- Timetable for User 3
(5),  -- Timetable for User 5
(6);  -- Timetable for User 6

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
    timetable_id BIGINT NOT NULL,
    FOREIGN KEY (timetable_id) REFERENCES timetables(timetable_id) ON DELETE CASCADE
);

-- Insert data into the events table
INSERT INTO events (event_name, event_date, event_start_time, event_end_time, event_location, event_owner_id, event_module_id, timetable_id)
VALUES 
('Internet Application of Engineering Lecture', '2024-12-03', '09:00:00', '11:00:00', '716', 3, 1, 1),  -- Timetable ID 1
('Internet Application of Engineering Workshop', '2024-12-06', '09:00:00', '11:00:00', '716', 3, 1, 1),  -- Timetable ID 1
('Robotics Club Meeting', '2024-12-04', '13:00:00', '15:00:00', 'Club Room 1', 2, 2, 2), -- Timetable ID 2
('Artificial Intelligence Lecture', '2024-12-05', '09:00:00', '11:00:00', 'Room 101', 3, 3, 3), -- Timetable ID 3
('Artificial Intelligence Workshop', '2024-12-08', '09:00:00', '11:00:00', 'Room 101', 3, 3, 3), -- Timetable ID 3
('Machine Learning Club Meeting', '2024-12-09', '13:00:00', '15:00:00', 'Club Room 2', 5, 4, 4), -- Timetable ID 4
('Deep Learning Club Meeting', '2024-12-10', '13:00:00', '15:00:00', 'Club Room 3', 6, 5, 5); -- Timetable ID 5
