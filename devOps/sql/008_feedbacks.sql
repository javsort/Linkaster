CREATE TABLE feedbacks (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    feedback_recipient_id BIGINT NOT NULL,
    feedback_sender_id BIGINT NOT NULL,
    feedback_anonymous bit NOT NULL,
    feedback_recipient_module BIGINT NOT NULL,
    feedback_contents VARCHAR(500)
);

-- add 3 sample data values: 
INSERT INTO feedbacks (
    feedback_recipient_id, feedback_sender_id, feedback_anonymous, 
    feedback_recipient_module, feedback_contents
) VALUES 
    (1, 2, false, 3, 'Example feedback 01.'),
    (4, 5, false, 6, 'Example feedback 02.'),
    (7, 0, true, 9, 'Example feedback 03.');