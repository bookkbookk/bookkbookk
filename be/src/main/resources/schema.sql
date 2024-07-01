DROP TABLE IF EXISTS comment_reaction;
DROP TABLE IF EXISTS comment;
DROP TABLE IF EXISTS bookmark_reaction;
DROP TABLE IF EXISTS bookmark;
DROP TABLE IF EXISTS topic;
DROP TABLE IF EXISTS chapter;
DROP TABLE IF EXISTS gathering;
DROP TABLE IF EXISTS member_book;
DROP TABLE IF EXISTS book;
DROP TABLE IF EXISTS book_club_member;
DROP TABLE IF EXISTS book_club;
DROP TABLE IF EXISTS member;

CREATE TABLE member
(
    member_id         BIGINT        NOT NULL AUTO_INCREMENT,
    login_type        VARCHAR(255)  NOT NULL,
    email             VARCHAR(255)  NOT NULL,
    nickname          VARCHAR(255)  NOT NULL,
    profile_image_url VARCHAR(1000) NOT NULL,
    CONSTRAINT pk_member PRIMARY KEY (member_id),
    CONSTRAINT uk_member_email UNIQUE KEY (email),
    CONSTRAINT uk_member_nickname UNIQUE KEY (nickname),
    CONSTRAINT ck_member_login_type CHECK (login_type IN ('GOOGLE'))
);

CREATE TABLE book_club
(
    book_club_id            BIGINT        NOT NULL AUTO_INCREMENT,
    creator_id              BIGINT        NOT NULL,
    status                  VARCHAR(255)  NOT NULL,
    name                    VARCHAR(255)  NOT NULL,
    profile_image_url       VARCHAR(1000) NOT NULL,
    created_time            TIMESTAMP     NOT NULL,
    closed_time             TIMESTAMP     NULL,
    upcoming_gathering_time TIMESTAMP     NULL,
    CONSTRAINT pk_book_club PRIMARY KEY (book_club_id),
    CONSTRAINT fk_book_club_member FOREIGN KEY (creator_id) REFERENCES member (member_id),
    CONSTRAINT uk_book_club_name UNIQUE KEY (name),
    CONSTRAINT ck_book_club_status CHECK (status IN ('OPEN', 'CLOSED'))
);

CREATE TABLE book_club_member
(
    book_club_member_id BIGINT NOT NULL AUTO_INCREMENT,
    book_club_id        BIGINT NOT NULL,
    member_id           BIGINT NOT NULL,
    CONSTRAINT pk_book_club_member PRIMARY KEY (book_club_member_id),
    CONSTRAINT fk_book_club_member_book_club FOREIGN KEY (book_club_id) REFERENCES book_club (book_club_id),
    CONSTRAINT fk_book_club_member_member FOREIGN KEY (member_id) REFERENCES member (member_id)
);

CREATE TABLE book
(
    book_id      BIGINT        NOT NULL AUTO_INCREMENT,
    book_club_id BIGINT        NOT NULL,
    status       VARCHAR(255)  NOT NULL,
    isbn         VARCHAR(255)  NOT NULL,
    title        VARCHAR(255)  NOT NULL,
    cover        VARCHAR(1000) NOT NULL,
    author       VARCHAR(255)  NOT NULL,
    category     VARCHAR(255)  NOT NULL,
    CONSTRAINT pk_book PRIMARY KEY (book_id),
    CONSTRAINT fk_book_book_club FOREIGN KEY (book_club_id) REFERENCES book_club (book_club_id),
    CONSTRAINT ck_book_status CHECK (status IN ('BEFORE_READING', 'READING', 'AFTER_READING'))
);

CREATE TABLE member_book
(
    member_book_id BIGINT NOT NULL AUTO_INCREMENT,
    member_id      BIGINT NOT NULL,
    book_id        BIGINT NOT NULL,
    CONSTRAINT pk_member_book PRIMARY KEY (member_book_id),
    CONSTRAINT fk_member_book_member FOREIGN KEY (member_id) REFERENCES member (member_id),
    CONSTRAINT fk_member_book_book FOREIGN KEY (book_id) REFERENCES book (book_id)
);

CREATE TABLE gathering
(
    gathering_id BIGINT       NOT NULL AUTO_INCREMENT,
    book_id      BIGINT       NOT NULL,
    start_time   TIMESTAMP    NOT NULL,
    place        VARCHAR(255) NOT NULL,
    CONSTRAINT pk_gathering PRIMARY KEY (gathering_id),
    CONSTRAINT fk_gathering_book FOREIGN KEY (book_id) REFERENCES book (book_id)
);

CREATE TABLE chapter
(
    chapter_id BIGINT       NOT NULL AUTO_INCREMENT,
    book_id    BIGINT       NOT NULL,
    status     VARCHAR(255) NOT NULL,
    title      VARCHAR(255) NULL,
    CONSTRAINT pk_chapter PRIMARY KEY (chapter_id),
    CONSTRAINT fk_chapter_book FOREIGN KEY (book_id) REFERENCES book (book_id),
    CONSTRAINT ck_chapter_status CHECK (status IN ('BEFORE_READING', 'READING', 'AFTER_READING'))
);

CREATE TABLE topic
(
    topic_id   BIGINT       NOT NULL AUTO_INCREMENT,
    chapter_id BIGINT       NOT NULL,
    title      VARCHAR(255) NULL,
    CONSTRAINT pk_topic PRIMARY KEY (topic_id),
    CONSTRAINT fk_topic_chapter FOREIGN KEY (chapter_id) REFERENCES chapter (chapter_id)
);

CREATE TABLE bookmark
(
    bookmark_id  BIGINT    NOT NULL AUTO_INCREMENT,
    topic_id     BIGINT    NOT NULL,
    writer_id    BIGINT    NOT NULL,
    page         INT       NOT NULL,
    contents     TEXT      NOT NULL,
    created_time TIMESTAMP NOT NULL,
    updated_time TIMESTAMP NULL,
    CONSTRAINT pk_bookmark PRIMARY KEY (bookmark_id),
    CONSTRAINT fk_bookmark_topic FOREIGN KEY (topic_id) REFERENCES topic (topic_id),
    CONSTRAINT fk_bookmark_member FOREIGN KEY (writer_id) REFERENCES member (member_id)
);

CREATE TABLE bookmark_reaction
(
    bookmark_reaction_id BIGINT       NOT NULL AUTO_INCREMENT,
    bookmark_id          BIGINT       NOT NULL,
    reactor_id           BIGINT       NOT NULL,
    reaction             VARCHAR(255) NOT NULL,
    CONSTRAINT pk_bookmark_reaction PRIMARY KEY (bookmark_reaction_id),
    CONSTRAINT fk_bookmark_reaction_bookmark FOREIGN KEY (bookmark_id) REFERENCES bookmark (bookmark_id),
    CONSTRAINT fk_bookmark_reaction_member FOREIGN KEY (reactor_id) REFERENCES member (member_id),
    CONSTRAINT ck_bookmark_reaction_reaction CHECK (reaction IN ('LIKE', 'LOVE', 'CLAP', 'CONGRATULATION', 'ROCKET'))
);

CREATE TABLE comment
(
    comment_id   BIGINT    NOT NULL AUTO_INCREMENT,
    bookmark_id  BIGINT    NOT NULL,
    writer_id    BIGINT    NOT NULL,
    contents     TEXT      NOT NULL,
    created_time TIMESTAMP NOT NULL,
    updated_time TIMESTAMP NULL,
    CONSTRAINT pk_comment PRIMARY KEY (comment_id),
    CONSTRAINT fk_comment_bookmark FOREIGN KEY (bookmark_id) REFERENCES bookmark (bookmark_id),
    CONSTRAINT fk_comment_member FOREIGN KEY (writer_id) REFERENCES member (member_id)
);

CREATE TABLE comment_reaction
(
    comment_reaction_id BIGINT       NOT NULL AUTO_INCREMENT,
    comment_id          BIGINT       NOT NULL,
    reactor_id          BIGINT       NOT NULL,
    reaction            VARCHAR(255) NOT NULL,
    CONSTRAINT pk_comment_reaction PRIMARY KEY (comment_reaction_id),
    CONSTRAINT fk_comment_reaction_comment FOREIGN KEY (comment_id) REFERENCES comment (comment_id),
    CONSTRAINT fk_comment_reaction_member FOREIGN KEY (reactor_id) REFERENCES member (member_id),
    CONSTRAINT ck_comment_reaction_reaction CHECK (reaction IN ('LIKE', 'LOVE', 'CLAP', 'CONGRATULATION', 'ROCKET'))
);
