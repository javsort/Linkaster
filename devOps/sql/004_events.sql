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
    FOREIGN KEY (event_owner_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (event_module_id) REFERENCES modules(module_id) ON DELETE CASCADE
);

-- Insert data of events
INSERT INTO events (event_name, event_date, event_start_time, event_end_time, event_location, event_owner_id, event_module_id)
VALUES 
('Internet Application of Engineering Lecture', '2024-12-03', '09:00:00', '11:00:00', '716', 3, 1),  -- Assuming module ID 1
('Internet Application of Engineering Workshop', '2024-12-06', '09:00:00', '11:00:00', '716', 3, 1),  -- Assuming module ID 1
('Robotics Club Meeting', '2024-12-04', '13:00:00', '15:00:00', 'Club Room 1', 2, 2); -- Assuming module ID 2
