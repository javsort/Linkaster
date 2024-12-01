-- Create the announcements table
CREATE TABLE announcements (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    announcement_message TEXT NOT NULL,
    announcement_date DATE NOT NULL,
    announcement_time VARCHAR(255) NOT NULL,
    announcement_owner_id BIGINT,
    announcement_module_id BIGINT,
    FOREIGN KEY (announcement_owner_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (announcement_module_id) REFERENCES modules(id) ON DELETE CASCADE
);

-- Insert data of announcements
INSERT INTO announcements (announcement_message, announcement_date, announcement_time, announcement_owner_id, announcement_module_id)
VALUES ('Welcome to the Internet Application of Engineering class!', '2024-12-03', '09:00:00', 3, 1),
       ('Welcome to the Robotics club!', '2024-12-04', '13:00:00', 2, 2);