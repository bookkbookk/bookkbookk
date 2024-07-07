package codesquad.bookkbookk.unit;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import codesquad.bookkbookk.common.config.QueryDslConfig;
import codesquad.bookkbookk.domain.auth.data.dto.BookClubMemberAuthInfo;
import codesquad.bookkbookk.domain.auth.repository.AuthorizationJdbcRepository;
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
import codesquad.bookkbookk.util.DatabaseCleaner;
import codesquad.bookkbookk.util.TestDataFactory;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({AuthorizationJdbcRepository.class, DatabaseCleaner.class, QueryDslConfig.class})
public class AuthorizationJdbcRepositoryTest {

    @Autowired
    private AuthorizationJdbcRepository authorizationCustomRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BookClubRepository bookClubRepository;

    @Autowired
    private BookClubMemberRepository bookClubMemberRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @AfterEach
    public void cleanUp() {
        databaseCleaner.execute();
    }

    @Test
    @DisplayName("Member와 BookClub이 저장되어 있고 매핑되어 있다면 AuthInfos 크기가 3이다.")
    void mappedBookClubMemberAuthInfosSizeIs3() {
        // given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);

        BookClub bookClub = TestDataFactory.createBookClub(member);
        bookClubRepository.save(bookClub);

        BookClubMember bookClubMember = new BookClubMember(bookClub, member);
        bookClubMemberRepository.save(bookClubMember);

        // when
        List<BookClubMemberAuthInfo> authInfos = authorizationCustomRepository
                .findBookClubMemberAuthsByBookClubIdAndMemberId(bookClub.getId(), member.getId());

        // then
        assertThat(authInfos).hasSize(3);
    }

    @Test
    @DisplayName("Member와 BookClub이 저장되어 있으나 매핑되지 않았다면 AuthInfos 크기가 2이다.")
    void unmappedBookClubMemberAuthInfosSizeIs2() {
        // given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);

        BookClub bookClub = TestDataFactory.createBookClub(member);
        bookClubRepository.save(bookClub);

        // when
        List<BookClubMemberAuthInfo> authInfos = authorizationCustomRepository
                .findBookClubMemberAuthsByBookClubIdAndMemberId(bookClub.getId(), member.getId());

        // then
        assertThat(authInfos).hasSize(2);
    }

    @Test
    @DisplayName("BookClub이 저장되어 있고 Member가 저장 안된 AuthInfos 크기는 1이다.")
    void unsavedBookClubAuthInfosSizeIs1() {
        // given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);

        BookClub bookClub = TestDataFactory.createBookClub(member);
        bookClubRepository.save(bookClub);

        // when
        List<BookClubMemberAuthInfo> authInfos = authorizationCustomRepository
                .findBookClubMemberAuthsByBookClubIdAndMemberId(bookClub.getId(), member.getId() + 1);

        // then
        assertThat(authInfos).hasSize(1);
    }

    @Test
    @DisplayName("BookClub이 저장되어 있지 않고 Member가 저장된 AuthInfos 크기는 1이다.")
    void unsavedMemberAuthInfosSizeIs1() {
        // given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);

        // when
        List<BookClubMemberAuthInfo> authInfos = authorizationCustomRepository
                .findBookClubMemberAuthsByBookClubIdAndMemberId(1L, member.getId());

        // then
        assertThat(authInfos).hasSize(1);
    }

    @Test
    @DisplayName("Member가 많은 BookClub에 속해 있어도 AuthInfos 크기에는 영향을 주지 않는다")
    void memberJoiningManyBookClubsDoesntAffectAuthInfosSize() {
        // given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);

        List<BookClub> bookClubs = TestDataFactory.createBookClubs(5, member);
        bookClubRepository.saveAll(bookClubs);

        List<BookClubMember> bookClubMembers = TestDataFactory.createBookClubMembers(bookClubs, member);
        bookClubMemberRepository.saveAll(bookClubMembers);

        // when
        List<BookClubMemberAuthInfo> authInfos = authorizationCustomRepository
                .findBookClubMemberAuthsByBookClubIdAndMemberId(bookClubs.get(2).getId(), member.getId());

        // then
        assertThat(authInfos).hasSize(3);
    }

    @Test
    @DisplayName("BookClub에 많은 Member가 속해 있어도 AuthInfos 크기에는 영향을 주지 않는다")
    void bookClubHasManyMembersDoesntAffectAuthInfosSize() {
        // given
        List<Member> members = TestDataFactory.createMembers(5);
        memberRepository.saveAll(members);

        BookClub bookClub = TestDataFactory.createBookClub(members.get(0));
        bookClubRepository.save(bookClub);

        List<BookClubMember> bookClubMembers = TestDataFactory.createBookClubMembers(bookClub, members);
        bookClubMemberRepository.saveAll(bookClubMembers);

        // when
        List<BookClubMemberAuthInfo> authInfos = authorizationCustomRepository
                .findBookClubMemberAuthsByBookClubIdAndMemberId(bookClub.getId(), members.get(2).getId());

        // then
        assertThat(authInfos).hasSize(3);
    }

    @Test
    @DisplayName("Chapter Id와 Member Id로 AuthInfos를 가져온다.")
    void findAuthInfosByChapterIdAndMemberId() {
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

        // when
        List<BookClubMemberAuthInfo> authInfos = authorizationCustomRepository
                .findBookClubMemberAuthsByChapterIdAndMemberId(chapter.getId(), member.getId());

        // then
        assertThat(authInfos).hasSize(3);
        assertThat(authInfos.get(0).getEntityId()).isNotNull();
    }

    @Test
    @DisplayName("BookClub에 속하지 않은 Member가 Chapter Id로 AuthInfos를 가져온다.")
    void findAuthInfosByChapterIdAndNotParticipatingMemberId() {
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

        // when
        List<BookClubMemberAuthInfo> authInfos = authorizationCustomRepository
                .findBookClubMemberAuthsByChapterIdAndMemberId(chapter.getId(), members.get(1).getId());

        // then
        assertThat(authInfos).hasSize(2);
    }

    @Test
    @DisplayName("Book Id와 Member Id로 AuthInfos를 가져온다.")
    void findAuthInfosByBookIdAndMemberId() {
        // given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);

        BookClub bookClub = TestDataFactory.createBookClub(member);
        bookClubRepository.save(bookClub);

        BookClubMember bookClubMember = new BookClubMember(bookClub, member);
        bookClubMemberRepository.save(bookClubMember);

        Book book = TestDataFactory.createBook(bookClub);
        bookRepository.save(book);

        // when
        List<BookClubMemberAuthInfo> authInfos = authorizationCustomRepository
                .findBookClubMemberAuthsByBookIdAndMemberId(book.getId(), member.getId());

        // then
        assertThat(authInfos).hasSize(3);
        assertThat(authInfos.get(0).getEntityId()).isNotNull();
    }

    @Test
    @DisplayName("BookClub에 속하지 않은 Member가 BookId Id로 AuthInfos를 가져온다.")
    void findAuthInfosByBookIdAndNotParticipatingMemberId() {
        // given
        List<Member> members = TestDataFactory.createMembers(2);
        memberRepository.saveAll(members);

        BookClub bookClub = TestDataFactory.createBookClub(members.get(0));
        bookClubRepository.save(bookClub);

        BookClubMember bookClubMember = new BookClubMember(bookClub, members.get(0));
        bookClubMemberRepository.save(bookClubMember);

        Book book = TestDataFactory.createBook(bookClub);
        bookRepository.save(book);

        // when
        List<BookClubMemberAuthInfo> authInfos = authorizationCustomRepository
                .findBookClubMemberAuthsByBookIdAndMemberId(book.getId(), members.get(1).getId());

        // then
        assertThat(authInfos).hasSize(2);
    }

    @Test
    @DisplayName("Gathering Id와 Member Id로 AuthInfos를 가져온다.")
    void findAuthInfosByGatheringIdAndMemberId() {
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

        // when
        List<BookClubMemberAuthInfo> authInfos = authorizationCustomRepository
                .findBookClubMemberAuthsByGatheringIdAndMemberId(gathering.getId(), member.getId());

        // then
        assertThat(authInfos).hasSize(3);
        assertThat(authInfos.get(0).getEntityId()).isNotNull();
    }

    @Test
    @DisplayName("BookClub에 속하지 않은 Member가 Gathering Id로 AuthInfos를 가져온다.")
    void findAuthInfosByGatheringIdAndNotParticipatingMemberId() {
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

        // when
        List<BookClubMemberAuthInfo> authInfos = authorizationCustomRepository
                .findBookClubMemberAuthsByGatheringIdAndMemberId(gathering.getId(), members.get(1).getId());

        // then
        assertThat(authInfos).hasSize(2);
    }

    @Test
    @DisplayName("Topic Id와 Member Id로 AuthInfos를 가져온다.")
    void findAuthInfosByTopicIdAndMemberId() {
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

        // when
        List<BookClubMemberAuthInfo> authInfos = authorizationCustomRepository
                .findBookClubMemberAuthsByTopicIdAndMemberId(topic.getId(), member.getId());

        // then
        assertThat(authInfos).hasSize(3);
        assertThat(authInfos.get(0).getEntityId()).isNotNull();
    }

    @Test
    @DisplayName("BookClub에 속하지 않은 Member가 Topic Id로 AuthInfos를 가져온다.")
    void findAuthInfosByTopicIdAndNotParticipatingMemberId() {
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

        // when
        List<BookClubMemberAuthInfo> authInfos = authorizationCustomRepository
                .findBookClubMemberAuthsByTopicIdAndMemberId(topic.getId(), members.get(1).getId());

        // then
        assertThat(authInfos).hasSize(2);
    }

    @Test
    @DisplayName("Bookmark Id와 Member Id로 AuthInfos를 가져온다.")
    void findAuthInfosByBookmarkIdAndMemberId() {
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

        // when
        List<BookClubMemberAuthInfo> authInfos = authorizationCustomRepository
                .findBookClubMemberAuthsByBookmarkIdAndMemberId(bookmark.getId(), member.getId());

        // then
        assertThat(authInfos).hasSize(3);
        assertThat(authInfos.get(0).getEntityId()).isNotNull();
    }

    @Test
    @DisplayName("BookClub에 속하지 않은 Member가 Bookmark Id로 AuthInfos를 가져온다.")
    void findAuthInfosByBookmarkIdAndNotParticipatingMemberId() {
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

        // when
        List<BookClubMemberAuthInfo> authInfos = authorizationCustomRepository
                .findBookClubMemberAuthsByBookmarkIdAndMemberId(bookmark.getId(), members.get(1).getId());

        // then
        assertThat(authInfos).hasSize(2);
    }

    @Test
    @DisplayName("Comment Id와 Member Id로 AuthInfos를 가져온다.")
    void findAuthInfosByCommentIdAndMemberId() {
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

        // when
        List<BookClubMemberAuthInfo> authInfos = authorizationCustomRepository
                .findBookClubMemberAuthsByCommentIdAndMemberId(comment.getId(), member.getId());

        // then
        assertThat(authInfos).hasSize(3);
        assertThat(authInfos.get(0).getEntityId()).isNotNull();
    }

    @Test
    @DisplayName("BookClub에 속하지 않은 Member가 Comment Id로 AuthInfos를 가져온다.")
    void findAuthInfosByCommentIdAndNotParticipatingMemberId() {
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

        // when
        List<BookClubMemberAuthInfo> authInfos = authorizationCustomRepository
                .findBookClubMemberAuthsByCommentIdAndMemberId(comment.getId(), members.get(1).getId());

        // then
        assertThat(authInfos).hasSize(2);
    }


}
