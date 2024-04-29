INSERT INTO member (login_type, email, nickname, profile_image_url)
VALUES ('GOOGLE', 'wlsdnjs0124@gmail.com', '나그', 'https://lh3.googleusercontent.com/a/ACg8ocI4UdR_y6c32pL94MdKHJwDGcGh2jU8a6eB-A6gQtSi0mRarzaG=s96-c
');

INSERT INTO book_club (created_time, creator_id, name, profile_image_url, status)
VALUES ('2023-12-31 00:00:00', 1, '테스트', 'https://lh3.googleusercontent.com/a/ACg8ocI4UdR_y6c32pL94MdKHJwDGcGh2jU8a6eB-A6gQtSi0mRarzaG=s96-c', 'OPEN');

INSERT INTO book_club_member (book_club_id, member_id)
VALUES (1, 1);

INSERT INTO book (book_club_id, author, category, cover, isbn, status, title)
VALUES (1, '신용권 (지은이)', '국내도서>컴퓨터/모바일>프로그래밍 언어>자바', 'https://image.aladin.co.kr/product/33323/64/coversum/k502938683_1.jpg', '9791169211901', 'BEFORE_READING', '혼자 공부하는 자바 - 1:1 과외하듯 배우는 프로그래밍 자습서, 개정판');

INSERT INTO member_book (book_id, member_id)
VALUES (1, 1);

INSERT INTO chapter (book_id, status, title)
VALUES (1, 'BEFORE_READING', '챕터 1'),
       (1, 'BEFORE_READING', '챕터 2'),
       (1, 'BEFORE_READING', '챕터 3');

INSERT INTO topic (chapter_id, title)
VALUES (1, '토픽 1'),
       (1, '토픽 2'),
       (2, '토픽 3'),
       (2, '토픽 4'),
       (2, '토픽 5'),
       (3, '토픽 6'),
       (3, '토픽 7');

