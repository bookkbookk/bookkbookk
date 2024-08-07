SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE bookmark;
DROP PROCEDURE IF EXISTS InsertBookmarks;

DELIMITER //

CREATE PROCEDURE InsertBookmarks()
BEGIN
    DECLARE counter INT DEFAULT 1;
    DECLARE base_date DATETIME DEFAULT '2000-01-01 00:00:00';

    WHILE counter <= 1000 DO
            INSERT INTO bookmark (topic_id, writer_id, page, contents, created_time)
            VALUES (1, 1, counter, 'content', DATE_ADD(base_date, INTERVAL counter DAY));
            SET counter = counter + 1;
        END WHILE;
END //

DELIMITER ;

CALL InsertBookmarks();

SET FOREIGN_KEY_CHECKS = 1;
