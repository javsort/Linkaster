CREATE TABLE private_chats (
    private_chat_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user1_email VARCHAR(255) NOT NULL,
    user1_name VARCHAR(255) NOT NULL,
    user1_public_key LONGTEXT NOT NULL,
    user1_role VARCHAR(255) NOT NULL,
    user1_id BIGINT NOT NULL,
    user2_email VARCHAR(255) NOT NULL,
    user2_name VARCHAR(255) NOT NULL,
    user2_public_key LONGTEXT NOT NULL,
    user2_role VARCHAR(255) NOT NULL,
    user2_id BIGINT NOT NULL,
    last_message_date DATE
);

CREATE TABLE private_messages (
    private_message_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    private_chat_id BIGINT NOT NULL,
    sender_id BIGINT NOT NULL,
    receiver_id BIGINT NOT NULL,
    encrypted_message VARCHAR(255) NOT NULL,
    timestamp TIMESTAMP NOT NULL,
    FOREIGN KEY (private_chat_id) REFERENCES private_chats(private_chat_id)
);

-- Insert example data into the table
INSERT INTO private_chats (
    user1_email, user1_name, user1_public_key, user1_role, user1_id,
    user2_email, user2_name, user2_public_key, user2_role, user2_id, last_message_date
) VALUES
('user1@lancaster.ac.uk', 'User One', 'user1_public_key_123', 'ADMIN', 5,
 'user2@lancaster.ac.uk', 'User Two', 'user2_public_key_456', 'TEACHER', 6, '2024-12-01'),
('user3@lancaster.ac.uk', 'User Three', 'user3_public_key_789', 'STUDENT', 7,
 'user4@lancaster.ac.uk', 'User Four', 'user4_public_key_101112', 'TEACHER', 8, '2024-12-01'),
('user5@lancaster.ac.uk', 'User Five', 'user5_public_key_131415', 'ADMIN', 9,
 'user6@lancaster.ac.uk', 'User Six', 'user6_public_key_161718', 'STUDENT', 10, '2024-12-01');

-- Insert example private messages
INSERT INTO private_messages (
    private_chat_id, sender_id, receiver_id, encrypted_message, timestamp
) VALUES
-- Messages for the first private chat
(1, 5, 6, 'encrypted_message_from_user1_to_user2', '2024-12-01 10:00:00'),
(1, 6, 5, 'encrypted_message_from_user2_to_user1', '2024-12-01 10:05:00'),
(1, 5, 6, 'encrypted_message_from_user1_to_user2_again', '2024-12-01 10:10:00'),

-- Messages for the second private chat
(2, 7, 8, 'encrypted_message_from_user3_to_user4', '2024-12-01 11:00:00'),
(2, 8, 7, 'encrypted_message_from_user4_to_user3', '2024-12-01 11:05:00'),

-- Messages for the third private chat
(3, 9, 10, 'encrypted_message_from_user5_to_user6', '2024-12-01 12:00:00'),
(3, 10, 9, 'encrypted_message_from_user6_to_user5', '2024-12-01 12:05:00'),
(3, 9, 10, 'encrypted_message_from_user5_to_user6_again', '2024-12-01 12:10:00');


-- Create the group chats table
CREATE TABLE group_chats (
    group_chat_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    module_AESKey VARCHAR(512) NOT NULL,
    module_id BIGINT,
    module_name VARCHAR(255),
    owner_user_id BIGINT,
    last_message_date DATE
);

CREATE TABLE group_chat_members (
    group_chat_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    public_key VARCHAR(255) NOT NULL,
    PRIMARY KEY (group_chat_id, user_id),
    FOREIGN KEY (group_chat_id) REFERENCES group_chats(group_chat_id)
);

CREATE TABLE group_messages (
    group_message_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    group_chat_id BIGINT NOT NULL,
    sender_id BIGINT NOT NULL,
    encrypted_message VARCHAR(255) NOT NULL,
    timestamp TIMESTAMP NOT NULL,
    FOREIGN KEY (group_chat_id) REFERENCES group_chats(group_chat_id)
);


-- Insert example group chats
INSERT INTO group_chats (module_AESKey, module_id, module_name, owner_user_id, last_message_date)
VALUES
('aes_key_1', 1, 'Internet Application of Engineering', 2, '2024-12-01'),
('aes_key_2', 2, 'Robotics', 2, '2024-12-01'),
('aes_key_3', 3, 'Artificial Intelligence', 3, '2024-12-01'),
('aes_key_4', 4, 'Machine Learning', 5, '2024-12-01'),
('aes_key_5', 5, 'Deep Learning', 6, '2024-12-01');

-- Insert members for the group chats
INSERT INTO group_chat_members (group_chat_id, user_id, public_key)
VALUES
-- Group Chat 1: Internet Application of Engineering
(1, 2, 'public_key_2'),
(1, 5, 'public_key_5'),
(1, 6, 'public_key_6'),
(1, 7, 'public_key_7'),

-- Group Chat 2: Robotics
(2, 2, 'public_key_2'),
(2, 8, 'public_key_8'),
(2, 9, 'public_key_9'),

-- Group Chat 3: Artificial Intelligence
(3, 3, 'public_key_3'),
(3, 10, 'public_key_10'),
(3, 11, 'public_key_11'),
(3, 12, 'public_key_12'),

-- Group Chat 4: Machine Learning
(4, 5, 'public_key_555'),
(4, 13, 'public_key_13'),
(4, 14, 'public_key_14'),

-- Group Chat 5: Deep Learning
(5, 6, 'public_key_777'),
(5, 15, 'public_key_15'),
(5, 16, 'public_key_16');

-- Insert group messages
INSERT INTO group_messages (group_chat_id, sender_id, encrypted_message, timestamp)
VALUES
(1, 2, 'encrypted_message_1', '2024-12-01 10:00:00'),
(1, 5, 'encrypted_message_2', '2024-12-01 10:05:00'),
(2, 8, 'encrypted_message_3', '2024-12-01 11:00:00'),
(3, 3, 'encrypted_message_4', '2024-12-01 12:00:00'),
(4, 5, 'encrypted_message_5', '2024-12-01 13:00:00'),
(5, 6, 'encrypted_message_6', '2024-12-01 14:00:00');