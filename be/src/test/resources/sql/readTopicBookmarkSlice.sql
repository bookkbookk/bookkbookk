INSERT INTO member (login_type, email, nickname, profile_image_url)
VALUES ('GOOGLE', 'nag@gmail.com', '나그', 'profile'),
       ('GOOGLE', 'zoey@naver.com', '조이', 'profile'),
       ('GOOGLE', 'asdf@naver.com', '익명', 'profile'),
       ('GOOGLE', 'gamgyul@naver.com', '감귤', 'profile');

INSERT INTO book_club (created_time, creator_id, name, profile_image_url, status)
VALUES ('2023-12-31 00:00:00', 1, '테스트', 'https://lh3.googleusercontent.com/a/ACg8ocI4UdR_y6c32pL94MdKHJwDGcGh2jU8a6eB-A6gQtSi0mRarzaG=s96-c', 'OPEN');

INSERT INTO book_club_member (book_club_id, member_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (1, 4);

INSERT INTO book (book_club_id, author, category, cover, isbn, status, title)
VALUES (1, '신용권 (지은이)', '국내도서>컴퓨터/모바일>프로그래밍 언어>자바', 'https://image.aladin.co.kr/product/33323/64/coversum/k502938683_1.jpg', '9791169211901', 'BEFORE_READING', '혼자 공부하는 자바 - 1:1 과외하듯 배우는 프로그래밍 자습서, 개정판');

INSERT INTO member_book (book_id, member_id)
VALUES (1, 1);

INSERT INTO chapter (book_id, status, title)
VALUES (1, 'BEFORE_READING', '챕터 1');

INSERT INTO topic (chapter_id, title)
VALUES (1, '토픽 1');

INSERT INTO bookmark (topic_id, writer_id, page, contents, created_time, updated_time)
VALUES (1, 1, 123, 'content', '2000-01-01 00:00:00', '2000-01-01 00:00:00'),
       (1, 2, 123, 'content', '2000-01-02 00:00:00', '2000-01-02 00:00:00'),
       (1, 3, 123, 'content', '2000-01-03 00:00:00', '2000-01-03 00:00:00'),
       (1, 1, 123, 'content', '2000-01-04 00:00:00', '2000-01-04 00:00:00'),
       (1, 2, 123, 'content', '2000-01-05 00:00:00', '2000-01-05 00:00:00');

       INSERT INTO bookmark_reaction (bookmark_id, reactor_id, reaction)
VALUES (1, 1, 'CLAP'),
       (2, 1, 'CONGRATULATION'),
       (3, 2, 'LOVE'),
       (3, 3, 'LOVE'),
       (3, 1, 'ROCKET'),
       (5, 2, 'LOVE'),
       (5, 4, 'CLAP'),
       (5, 1, 'LOVE');

INSERT INTO comment (bookmark_id, writer_id, contents, created_time, updated_time)
VALUES (1, 1, 'content', '2000-01-01 00:00:00', '2000-01-01 00:00:00'),
       (2, 2, 'content', '2000-01-02 00:00:00', '2000-01-02 00:00:00'),
       (3, 1, 'content', '2000-01-03 00:00:00', '2000-01-03 00:00:00'),
       (3, 2, 'content', '2000-01-04 00:00:00', '2000-01-04 00:00:00'),
       (3, 3, 'content', '2000-01-05 00:00:00', '2000-01-05 00:00:00'),
       (3, 1, 'content', '2000-01-06 00:00:00', '2000-01-06 00:00:00'),
       (4, 1, 'content', '2000-01-07 00:00:00', '2000-01-07 00:00:00'),
       (4, 1, 'content', '2000-01-08 00:00:00', '2000-01-08 00:00:00'),
       (5, 2, 'content', '2000-01-09 00:00:00', '2000-01-09 00:00:00'),
       (5, 3, 'content', '2000-01-10 00:00:00', '2000-01-10 00:00:00'),
       (5, 1, 'content', '2000-01-11 00:00:00', '2000-01-11 00:00:00'),
       (5, 4, 'content', '2000-01-12 00:00:00', '2000-01-12 00:00:00'),
       (5, 1, 'content', '2000-01-13 00:00:00', '2000-01-13 00:00:00');

INSERT INTO comment_reaction (comment_id, reactor_id, reaction)
VALUES (1, 1, 'CLAP'),
       (4, 2, 'LIKE'),
       (4, 3, 'CONGRATULATION'),
       (4, 2, 'LOVE'),
       (4, 1, 'LOVE'),
       (4, 3, 'LOVE'),
       (6, 1, 'ROCKET'),
       (6, 4, 'LIKE'),
       (6, 2, 'LOVE'),
       (7, 3, 'LOVE'),
       (7, 2, 'CLAP'),
       (7, 1, 'LOVE'),
       (7, 4, 'ROCKET'),
       (7, 2, 'LIKE'),
       (8, 2, 'CLAP'),
       (8, 1, 'CLAP'),
       (11, 4, 'CONGRATULATION'),
       (11, 3, 'LOVE'),
       (11, 1, 'LIKE'),
       (13, 3, 'ROCKET'),
       (13, 1, 'LIKE'),
       (13, 2, 'LIKE'),
       (13, 4, 'CONGRATULATION'),
       (13, 2, 'LOVE');
