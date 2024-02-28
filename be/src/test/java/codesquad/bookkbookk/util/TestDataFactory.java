package codesquad.bookkbookk.util;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import codesquad.bookkbookk.domain.auth.data.type.LoginType;
import codesquad.bookkbookk.domain.book.data.entity.Book;
import codesquad.bookkbookk.domain.bookclub.data.entity.BookClub;
import codesquad.bookkbookk.domain.bookmark.data.entity.Bookmark;
import codesquad.bookkbookk.domain.chapter.data.entity.Chapter;
import codesquad.bookkbookk.domain.comment.data.entity.Comment;
import codesquad.bookkbookk.domain.gathering.data.entity.Gathering;
import codesquad.bookkbookk.domain.member.data.entity.Member;
import codesquad.bookkbookk.domain.topic.data.entity.Topic;

public class TestDataFactory {

    public static Member createMember() {
        return Member.builder()
                .email("email@email.email")
                .loginType(LoginType.GOOGLE)
                .nickname("nickname")
                .profileImageUrl("profile")
                .build();
    }

    public static List<Member> createMembers(int count) {
        return IntStream.range(0, count)
                .mapToObj(number -> Member.builder()
                        .email("email@email.email " + number)
                        .loginType(LoginType.GOOGLE)
                        .nickname("nickname " + number)
                        .profileImageUrl("profile" + number)
                        .build())
                .collect(Collectors.toUnmodifiableList());
    }

    public static BookClub createBookClub(Member creator) {
        return BookClub.builder()
                .creatorId(creator.getId())
                .name("name")
                .profileImageUrl("image.url")
                .build();
    }

    public static List<BookClub> createBookClubs(int count, Member creator) {
        return IntStream.range(0, count)
                .mapToObj(number -> BookClub.builder()
                        .creatorId(creator.getId())
                        .name("name " + number)
                        .profileImageUrl("image.url " + number)
                        .build())
                .collect(Collectors.toUnmodifiableList());
    }

    public static Book createBook(BookClub bookClub) {
        return Book.builder()
                .title("title")
                .bookClub(bookClub)
                .author("author")
                .category("category")
                .cover("cover")
                .isbn("9791169210607")
                .build();
    }

    public static Book createBook(int number, BookClub bookClub) {
        return Book.builder()
                .title("title " + number)
                .bookClub(bookClub)
                .author("author " + number)
                .category("category " + number)
                .cover("cover " + number)
                .isbn("9791169210607")
                .build();
    }

    public static List<Book> createBooks(int count, BookClub bookClub) {
        return IntStream.range(0, count)
                .mapToObj(number -> Book.builder()
                        .title("title " + number)
                        .bookClub(bookClub)
                        .author("author " + number)
                        .category("category " + number)
                        .cover("cover " + number)
                        .isbn("9791169210607")
                        .build())
                .collect(Collectors.toUnmodifiableList());
    }

    public static Chapter createChapter(Book book) {
        return new Chapter(book, "chapter");
    }

    public static List<Chapter> createChapters(int count, Book book) {
        return IntStream.range(0, count)
                .mapToObj(number -> new Chapter(book, "chapter " + number))
                .collect(Collectors.toUnmodifiableList());
    }

    public static Topic createTopic(Chapter chapter) {
        return new Topic(chapter, "topic");
    }

    public static List<Topic> createTopics(int count, Chapter chapter) {
        return IntStream.range(0, count)
                .mapToObj(number -> new Topic(chapter, "topic " + number))
                .collect(Collectors.toUnmodifiableList());
    }

    public static Bookmark createBookmark(Member writer, Topic topic) {
        return Bookmark.builder()
                .writer(writer)
                .topic(topic)
                .title("title")
                .contents("contents")
                .build();
    }

    public static List<Bookmark> createBookmarks(int count, Member writer, Topic topic) {
        return IntStream.range(0, count)
                .mapToObj(number -> Bookmark.builder()
                        .writer(writer)
                        .topic(topic)
                        .title("title " + number)
                        .contents("contents " + number)
                        .build())
                .collect(Collectors.toUnmodifiableList());
    }

    public static Comment createComment(Bookmark bookmark, Member writer) {
        return new Comment(bookmark, writer, "contents");
    }

    public static List<Comment> createComments(int count, Bookmark bookmark, Member writer) {
        return IntStream.range(0, count)
                .mapToObj(number -> new Comment(bookmark, writer, "contents " + number))
                .collect(Collectors.toUnmodifiableList());
    }

    public static Gathering createGathering(Book book) {
        return new Gathering(book, LocalDateTime.of(2023, 12, 25, 13, 30),
                "place");
    }

    public static List<Gathering> createGatherings(int count, Book book) {
        return IntStream.range(0, count)
                .mapToObj(number -> new Gathering(book, LocalDateTime.of(2023, 12, 25, 13, 30),
                        "place " + number))
                .collect(Collectors.toUnmodifiableList());
    }



}
