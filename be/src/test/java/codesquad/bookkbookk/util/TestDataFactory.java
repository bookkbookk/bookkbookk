package codesquad.bookkbookk.util;

import codesquad.bookkbookk.domain.auth.data.type.LoginType;
import codesquad.bookkbookk.domain.book.data.entity.Book;
import codesquad.bookkbookk.domain.bookclub.data.entity.BookClub;
import codesquad.bookkbookk.domain.bookmark.data.entity.Bookmark;
import codesquad.bookkbookk.domain.chapter.data.entity.Chapter;
import codesquad.bookkbookk.domain.comment.data.entity.Comment;
import codesquad.bookkbookk.domain.member.data.entity.Member;
import codesquad.bookkbookk.domain.topic.data.entity.Topic;

public class TestDataFactory {

    public static Member createMember() {
        return Member.builder()
                .email("nag@email.com")
                .loginType(LoginType.GOOGLE)
                .nickname("nag")
                .profileImgUrl("profile")
                .build();
    }

    public static Member createAnotherMember() {
        return Member.builder()
                .email("gamgyul@email.com")
                .loginType(LoginType.GOOGLE)
                .nickname("gamgyul")
                .profileImgUrl("profile")
                .build();
    }

    public static BookClub createBookClub() {
        return BookClub.builder()
                .creatorId(1L)
                .name("Test Book Club")
                .profileImgUrl("image.url")
                .build();
    }

    public static Book createBook1(BookClub bookClub) {
        return Book.builder()
                .title("신데렐라")
                .bookClub(bookClub)
                .author("감귤")
                .category("미스테리")
                .cover("Cinderella")
                .isbn("1231231231231")
                .build();

    }

    public static Book createBook2(BookClub bookClub) {
        return Book.builder()
                .title("개똥벌레")
                .bookClub(bookClub)
                .author("나그")
                .category("스릴러")
                .cover("gaeddong")
                .isbn("1231231231232")
                .build();

    }

    public static Chapter createChapter1(Book book) {
        return new Chapter(book, "chapter 1");
    }

    public static Chapter createChapter2(Book book) {
        return new Chapter(book, "chapter 2");
    }

    public static Topic createTopic1(Chapter chapter) {
        return new Topic(chapter, "topic 1");
    }

    public static Topic createTopic2(Chapter chapter) {
        return new Topic(chapter, "topic 2");
    }

    public static Bookmark createBookmark(Member writer, Topic topic) {
        return Bookmark.builder()
                .writer(writer)
                .topic(topic)
                .title("title")
                .content("content")
                .build();
    }

    public static Comment createComment(Bookmark bookmark, Member writer) {
        return new Comment(bookmark, writer, "content");
    }

}
