SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE comment;
DROP PROCEDURE IF EXISTS InsertComments;

DELIMITER //

CREATE PROCEDURE InsertComments()
BEGIN
    DECLARE counter INT DEFAULT 1;
    DECLARE bookmark_id INT DEFAULT 1;
    DECLARE base_date DATETIME DEFAULT '2000-01-01 00:00:00';

    WHILE counter <= 20000 DO
            INSERT INTO comment (bookmark_id, writer_id, contents, created_time)
            VALUES (bookmark_id, 1, 'content', DATE_ADD(base_date, INTERVAL counter SECOND));
            SET bookmark_id = (bookmark_id % 100) + 1;
            SET counter = counter + 1;
        END WHILE;
END //

DELIMITER ;

CALL InsertComments();

SET FOREIGN_KEY_CHECKS = 1;
