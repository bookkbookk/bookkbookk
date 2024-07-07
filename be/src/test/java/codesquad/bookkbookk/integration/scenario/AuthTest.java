package codesquad.bookkbookk.integration.scenario;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import codesquad.bookkbookk.common.error.exception.EntityNotFountException;
import codesquad.bookkbookk.common.error.exception.MemberNotInBookClubException;
import codesquad.bookkbookk.domain.auth.service.AuthorizationService;
import codesquad.bookkbookk.domain.book.data.entity.Book;
import codesquad.bookkbookk.domain.book.repository.BookRepository;
import codesquad.bookkbookk.domain.bookclub.data.entity.BookClub;
import codesquad.bookkbookk.domain.bookclub.repository.BookClubRepository;
import codesquad.bookkbookk.domain.bookmark.data.entity.Bookmark;
import codesquad.bookkbookk.domain.chapter.data.entity.Chapter;
import codesquad.bookkbookk.domain.comment.data.entity.Comment;
import codesquad.bookkbookk.domain.gathering.data.entity.Gathering;
import codesquad.bookkbookk.domain.mapping.entity.BookClubMember;
import codesquad.bookkbookk.domain.mapping.repository.BookClubMemberRepository;
import codesquad.bookkbookk.domain.member.data.entity.Member;
import codesquad.bookkbookk.domain.member.repository.MemberRepository;
import codesquad.bookkbookk.domain.topic.data.entity.Topic;
import codesquad.bookkbookk.util.IntegrationTest;
import codesquad.bookkbookk.util.TestDataFactory;

public class AuthTest extends IntegrationTest {

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BookClubRepository bookClubRepository;

    @Autowired
    private BookClubMemberRepository bookClubMemberRepository;

    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("BookClubId로 BookClub 접근을 인가한다.")
    void authorizeBookClubMembershipByBookClubId() {
        // given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);

        BookClub bookClub = TestDataFactory.createBookClub(member);
        bookClubRepository.save(bookClub);

        BookClubMember bookClubMember = new BookClubMember(bookClub, member);
        bookClubMemberRepository.save(bookClubMember);

        // when & then
        assertThatCode(
                () -> authorizationService.authorizeBookClubMembershipByBookClubId(bookClub.getId(), member.getId())
        ).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("매핑 엔티티가 만들어 지지 않은 BookClub의 Id로 BookClub 인가를 요청하면 예외가 발생한다.")
    void authorizeBookClubMembershipByUnmappedBookClubId() {
        // given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);

        BookClub bookClub = TestDataFactory.createBookClub(member);
        bookClubRepository.save(bookClub);

        // when & then
        assertThatThrownBy(
                () -> authorizationService.authorizeBookClubMembershipByBookClubId(bookClub.getId(), member.getId())
        ).isInstanceOf(MemberNotInBookClubException.class);
    }

    @Test
    @DisplayName("저장되지 않은 BookClub의 Id로 BookClub 인가를 요청하면 예외가 발생한다.")
    void authorizeBookClubMembershipByUnsavedBookClubId() {
        // given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);

        // when & then
        assertThatThrownBy(
                () -> authorizationService.authorizeBookClubMembershipByBookClubId(1L, member.getId())
        ).isInstanceOf(EntityNotFountException.class);
    }

    @Test
    @DisplayName("저장되지 않은 Member의 Id로 BookClub 인가를 요청하면 예외가 발생한다.")
    void authorizeBookClubMembershipByUnsavedMemberId() {
        // given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);

        BookClub bookClub = TestDataFactory.createBookClub(member);
        bookClubRepository.save(bookClub);

        // when & then
        assertThatThrownBy(
                () -> authorizationService.authorizeBookClubMembershipByBookClubId(bookClub.getId(), member.getId() + 1)
        ).isInstanceOf(EntityNotFountException.class);
    }

    @Test
    @DisplayName("많은 BookClub에 참여한 Member Id로 인가한다.")
    void authorizeBookClubMembershipByManyJoinedMemberId() {
        // given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);

        List<BookClub> bookClubs = TestDataFactory.createBookClubs(5, member);
        bookClubRepository.saveAll(bookClubs);

        List<BookClubMember> bookClubMembers = TestDataFactory.createBookClubMembers(bookClubs, member);
        bookClubMemberRepository.saveAll(bookClubMembers);

        // when & then
        assertThatCode(
                () -> authorizationService.authorizeBookClubMembershipByBookClubId(bookClubs.get(2).getId(), member.getId())
        ).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("많은 Member가 참여한 BookClub Id로 인가한다.")
    void authorizeBookClubMembershipByManyJoinedBookClubId() {
        // given
        List<Member> members = TestDataFactory.createMembers(5);
        memberRepository.saveAll(members);

        BookClub bookClub = TestDataFactory.createBookClub(members.get(0));
        bookClubRepository.save(bookClub);

        List<BookClubMember> bookClubMembers = TestDataFactory.createBookClubMembers(bookClub, members);
        bookClubMemberRepository.saveAll(bookClubMembers);

        // when & then
        assertThatCode(
                () -> authorizationService.authorizeBookClubMembershipByBookClubId(bookClub.getId(), members.get(2).getId())
        ).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Book Id와 Member Id로 Book Club 접근을 인가한다.")
    void authorizeBookClubMembershipByBookIdAndMemberId() {
        // given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);

        BookClub bookClub = TestDataFactory.createBookClub(member);
        bookClubRepository.save(bookClub);

        BookClubMember bookClubMember = new BookClubMember(bookClub, member);
        bookClubMemberRepository.save(bookClubMember);

        Book book = TestDataFactory.createBook(bookClub);
        bookRepository.save(book);

        // when & then
        assertThatCode(
                () -> authorizationService.authorizeBookClubMembershipByBookId(book.getId(), member.getId())
        ).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Book Id와 Member Id로 Book Club 인가가 실패한다.")
    void denyBookClubAuthorizationByBookIdAndMemberId() {
        // given
        List<Member> members = TestDataFactory.createMembers(2);
        memberRepository.saveAll(members);

        BookClub bookClub = TestDataFactory.createBookClub(members.get(0));
        bookClubRepository.save(bookClub);

        BookClubMember bookClubMember = new BookClubMember(bookClub, members.get(0));
        bookClubMemberRepository.save(bookClubMember);

        Book book = TestDataFactory.createBook(bookClub);
        bookRepository.save(book);

        // when & then
        assertThatThrownBy(
                () -> authorizationService.authorizeBookClubMembershipByBookId(book.getId(), members.get(1).getId())
        ).isInstanceOf(MemberNotInBookClubException.class);
    }

    @Test
    @DisplayName("Gathering Id와 Member Id로 Book Club 접근을 인가한다.")
    void authorizeBookClubMembershipByGatheringIdAndMemberId() {
        // given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);

        BookClub bookClub = TestDataFactory.createBookClub(member);
        bookClubRepository.save(bookClub);

        BookClubMember bookClubMember = new BookClubMember(bookClub, member);
        bookClubMemberRepository.save(bookClubMember);

        Book book = TestDataFactory.createBook(bookClub);
        Gathering gathering = TestDataFactory.createGathering(book);
        book.addGathering(gathering);
        bookRepository.save(book);

        // when & then
        assertThatCode(
                () -> authorizationService.authorizeBookClubMembershipByGatheringId(gathering.getId(), member.getId())
        ).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Gathering Id와 Member Id로 Book Club 인가가 실패한다.")
    void denyBookClubAuthorizationByGatheringIdAndMemberId() {
        // given
        List<Member> members = TestDataFactory.createMembers(2);
        memberRepository.saveAll(members);

        BookClub bookClub = TestDataFactory.createBookClub(members.get(0));
        bookClubRepository.save(bookClub);

        BookClubMember bookClubMember = new BookClubMember(bookClub, members.get(0));
        bookClubMemberRepository.save(bookClubMember);

        Book book = TestDataFactory.createBook(bookClub);
        Gathering gathering = TestDataFactory.createGathering(book);
        book.addGathering(gathering);
        bookRepository.save(book);

        // when & then
        assertThatThrownBy(
                () -> authorizationService.authorizeBookClubMembershipByGatheringId(gathering.getId(), members.get(1).getId())
        ).isInstanceOf(MemberNotInBookClubException.class);
    }

    @Test
    @DisplayName("Chapter Id와 Member Id로 Book Club 접근을 인가한다.")
    void authorizeBookClubMembershipByChapterIdAndMemberId() {
        // given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);

        BookClub bookClub = TestDataFactory.createBookClub(member);
        bookClubRepository.save(bookClub);

        BookClubMember bookClubMember = new BookClubMember(bookClub, member);
        bookClubMemberRepository.save(bookClubMember);

        Book book = TestDataFactory.createBook(bookClub);
        Chapter chapter = TestDataFactory.createChapter(book);
        book.addChapter(chapter);
        bookRepository.save(book);

        // when & then
        assertThatCode(
                () -> authorizationService.authorizeBookClubMembershipByChapterId(chapter.getId(), member.getId())
        ).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Chapter Id와 Member Id로 Book Club 인가가 실패한다.")
    void denyBookClubAuthorizationByChapterIdAndMemberId() {
        // given
        List<Member> members = TestDataFactory.createMembers(2);
        memberRepository.saveAll(members);

        BookClub bookClub = TestDataFactory.createBookClub(members.get(0));
        bookClubRepository.save(bookClub);

        BookClubMember bookClubMember = new BookClubMember(bookClub, members.get(0));
        bookClubMemberRepository.save(bookClubMember);

        Book book = TestDataFactory.createBook(bookClub);
        Chapter chapter = TestDataFactory.createChapter(book);
        book.addChapter(chapter);
        bookRepository.save(book);

        // when & then
        assertThatThrownBy(
                () -> authorizationService.authorizeBookClubMembershipByChapterId(chapter.getId(), members.get(1).getId())
        ).isInstanceOf(MemberNotInBookClubException.class);
    }

    @Test
    @DisplayName("Topic Id와 Member Id로 Book Club 접근을 인가한다.")
    void authorizeBookClubMembershipByTopicIdAndMemberId() {
        // given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);

        BookClub bookClub = TestDataFactory.createBookClub(member);
        bookClubRepository.save(bookClub);

        BookClubMember bookClubMember = new BookClubMember(bookClub, member);
        bookClubMemberRepository.save(bookClubMember);

        Book book = TestDataFactory.createBook(bookClub);
        Chapter chapter = TestDataFactory.createChapter(book);
        book.addChapter(chapter);
        Topic topic = TestDataFactory.createTopic(chapter);
        chapter.addTopic(topic);
        bookRepository.save(book);

        // when & then
        assertThatCode(
                () -> authorizationService.authorizeBookClubMembershipByTopicId(topic.getId(), member.getId())
        ).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Topic Id와 Member Id로 Book Club 인가가 실패한다.")
    void denyBookClubAuthorizationByTopicIdAndMemberId() {
        // given
        List<Member> members = TestDataFactory.createMembers(2);
        memberRepository.saveAll(members);

        BookClub bookClub = TestDataFactory.createBookClub(members.get(0));
        bookClubRepository.save(bookClub);

        BookClubMember bookClubMember = new BookClubMember(bookClub, members.get(0));
        bookClubMemberRepository.save(bookClubMember);

        Book book = TestDataFactory.createBook(bookClub);
        Chapter chapter = TestDataFactory.createChapter(book);
        book.addChapter(chapter);
        Topic topic = TestDataFactory.createTopic(chapter);
        chapter.addTopic(topic);
        bookRepository.save(book);

        // when & then
        assertThatThrownBy(
                () -> authorizationService.authorizeBookClubMembershipByTopicId(topic.getId(), members.get(1).getId())
        ).isInstanceOf(MemberNotInBookClubException.class);
    }

    @Test
    @DisplayName("Bookmark Id와 Member Id로 Book Club 접근을 인가한다.")
    void authorizeBookClubMembershipByBookmarkIdAndMemberId() {
        // given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);

        BookClub bookClub = TestDataFactory.createBookClub(member);
        bookClubRepository.save(bookClub);

        BookClubMember bookClubMember = new BookClubMember(bookClub, member);
        bookClubMemberRepository.save(bookClubMember);

        Book book = TestDataFactory.createBook(bookClub);
        Chapter chapter = TestDataFactory.createChapter(book);
        book.addChapter(chapter);
        Topic topic = TestDataFactory.createTopic(chapter);
        chapter.addTopic(topic);
        Bookmark bookmark = TestDataFactory.createBookmark(member, topic);
        topic.addBookmark(bookmark);
        bookRepository.save(book);

        // when & then
        assertThatCode(
                () -> authorizationService.authorizeBookClubMembershipByBookmarkId(bookmark.getId(), member.getId())
        ).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Bookmark Id와 Member Id로 Book Club 인가가 실패한다.")
    void denyBookClubAuthorizationByBookmarkIdAndMemberId() {
        // given
        List<Member> members = TestDataFactory.createMembers(2);
        memberRepository.saveAll(members);

        BookClub bookClub = TestDataFactory.createBookClub(members.get(0));
        bookClubRepository.save(bookClub);

        BookClubMember bookClubMember = new BookClubMember(bookClub, members.get(0));
        bookClubMemberRepository.save(bookClubMember);

        Book book = TestDataFactory.createBook(bookClub);
        Chapter chapter = TestDataFactory.createChapter(book);
        book.addChapter(chapter);
        Topic topic = TestDataFactory.createTopic(chapter);
        chapter.addTopic(topic);
        Bookmark bookmark = TestDataFactory.createBookmark(members.get(0), topic);
        topic.addBookmark(bookmark);
        bookRepository.save(book);

        // when & then
        assertThatThrownBy(
                () -> authorizationService.authorizeBookClubMembershipByBookmarkId(bookmark.getId(), members.get(1).getId())
        ).isInstanceOf(MemberNotInBookClubException.class);
    }

    @Test
    @DisplayName("Comment Id와 Member Id로 Book Club 접근을 인가한다.")
    void authorizeBookClubMembershipByCommentIdAndMemberId() {
        // given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);

        BookClub bookClub = TestDataFactory.createBookClub(member);
        bookClubRepository.save(bookClub);

        BookClubMember bookClubMember = new BookClubMember(bookClub, member);
        bookClubMemberRepository.save(bookClubMember);

        Book book = TestDataFactory.createBook(bookClub);
        Chapter chapter = TestDataFactory.createChapter(book);
        book.addChapter(chapter);
        Topic topic = TestDataFactory.createTopic(chapter);
        chapter.addTopic(topic);
        Bookmark bookmark = TestDataFactory.createBookmark(member, topic);
        topic.addBookmark(bookmark);
        Comment comment = TestDataFactory.createComment(bookmark, member);
        bookmark.addComment(comment);
        bookRepository.save(book);

        // when & then
        assertThatCode(
                () -> authorizationService.authorizeBookClubMembershipByCommentId(comment.getId(), member.getId())
        ).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Comment Id와 Member Id로 Book Club 인가가 실패한다.")
    void denyBookClubAuthorizationByCommentIdAndMemberId() {
        // given
        List<Member> members = TestDataFactory.createMembers(2);
        memberRepository.saveAll(members);

        BookClub bookClub = TestDataFactory.createBookClub(members.get(0));
        bookClubRepository.save(bookClub);

        BookClubMember bookClubMember = new BookClubMember(bookClub, members.get(0));
        bookClubMemberRepository.save(bookClubMember);

        Book book = TestDataFactory.createBook(bookClub);
        Chapter chapter = TestDataFactory.createChapter(book);
        book.addChapter(chapter);
        Topic topic = TestDataFactory.createTopic(chapter);
        chapter.addTopic(topic);
        Bookmark bookmark = TestDataFactory.createBookmark(members.get(0), topic);
        topic.addBookmark(bookmark);
        Comment comment = TestDataFactory.createComment(bookmark, members.get(0));
        bookmark.addComment(comment);
        bookRepository.save(book);

        // when & then
        assertThatThrownBy(
                () -> authorizationService.authorizeBookClubMembershipByCommentId(comment.getId(), members.get(1).getId())
        ).isInstanceOf(MemberNotInBookClubException.class);
    }

}
