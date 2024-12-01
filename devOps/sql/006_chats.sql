CREATE TABLE private_chats (
    private_chat_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user1_email VARCHAR(255) NOT NULL,
    user1_name VARCHAR(255) NOT NULL,
    user1_public_key VARCHAR(255) NOT NULL,
    user1_role VARCHAR(255) NOT NULL,
    user1_id BIGINT NOT NULL,
    user2_email VARCHAR(255) NOT NULL,
    user2_name VARCHAR(255) NOT NULL,
    user2_public_key VARCHAR(255) NOT NULL,
    user2_role VARCHAR(255) NOT NULL,
    user2_id BIGINT NOT NULL,
    last_message_date DATE
);

-- Insert example data into the table
INSERT INTO private_chats (
    user1_email, user1_name, user1_public_key, user1_role, user1_id,
    user2_email, user2_name, user2_public_key, user2_role, user2_id, last_message_date
) VALUES
('user1@example.com', 'User One', 'user1_public_key_123', 'ADMIN', 5,
 'user2@example.com', 'User Two', 'user2_public_key_456', 'TEACHER', 6, '2024-12-01'),
('user3@example.com', 'User Three', 'user3_public_key_789', 'STUDENT', 7,
 'user4@example.com', 'User Four', 'user4_public_key_101112', 'TEACHER', 8, '2024-12-01'),
('user5@example.com', 'User Five', 'user5_public_key_131415', 'ADMIN', 9,
 'user6@example.com', 'User Six', 'user6_public_key_161718', 'STUDENT', 10, '2024-12-01');