package codesquad.bookkbookk.auth.repository;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import codesquad.bookkbookk.IntegrationTest;
import codesquad.bookkbookk.domain.auth.repository.AuthorizationRepository;
import codesquad.bookkbookk.domain.book.data.entity.Book;
import codesquad.bookkbookk.domain.book.repository.BookRepository;
import codesquad.bookkbookk.domain.bookclub.data.entity.BookClub;
import codesquad.bookkbookk.domain.bookclub.repository.BookClubRepository;
import codesquad.bookkbookk.domain.bookmark.repository.BookmarkRepository;
import codesquad.bookkbookk.domain.chapter.data.entity.Chapter;
import codesquad.bookkbookk.domain.chapter.repository.ChapterRepository;
import codesquad.bookkbookk.domain.comment.repository.CommentRepository;
import codesquad.bookkbookk.domain.mapping.entity.BookClubMember;
import codesquad.bookkbookk.domain.mapping.repository.BookClubMemberRepository;
import codesquad.bookkbookk.domain.member.data.entity.Member;
import codesquad.bookkbookk.domain.member.repository.MemberRepository;
import codesquad.bookkbookk.domain.topic.data.entity.Topic;
import codesquad.bookkbookk.domain.topic.repository.TopicRepository;
import codesquad.bookkbookk.util.TestDataFactory;

public class AuthorizationRepositoryTest extends IntegrationTest {

    @Autowired
    private AuthorizationRepository authorizationRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private BookClubRepository bookClubRepository;
    @Autowired
    private BookClubMemberRepository bookClubMemberRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ChapterRepository chapterRepository;
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private BookmarkRepository bookmarkRepository;
    @Autowired
    private CommentRepository commentRepository;

    @Test
    @DisplayName("북클럽 멤버가 저장되었을 떄 멤버의 아이디와 북클럽 아이디로 조회에 성공한다.")
    void existsBookClubMemberByBookClubIdAndMemberIdSucceed() {
        // given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);

        BookClub bookClub = TestDataFactory.createBookClub(member);
        bookClubRepository.save(bookClub);

        BookClubMember bookClubMember = new BookClubMember(bookClub, member);
        bookClubMemberRepository.save(bookClubMember);

        // when
        boolean result = authorizationRepository.existsBookClubMemberByMemberIdAndBookClubId(member.getId(),
                bookClub.getId());

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("북클럽 멤버가 저장되지 않으면 멤버의 아이디와 북클럽 아이디로 조회했을 때 거짓이 나온다.")
    void existsBookClubMemberByBookClubIdAndMemberIdFail() {
        // given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);

        BookClub bookClub = TestDataFactory.createBookClub(member);
        bookClubRepository.save(bookClub);

        // when
        boolean result = authorizationRepository.existsBookClubMemberByMemberIdAndBookClubId(member.getId(),
                bookClub.getId());

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("native query에 JOIN문 없이 '.'으로 하부항목을 검색하면 INNER JOIN이 붙는다.")
    void addAutoInnerJoinToNativeQuery() {
        // given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);

        BookClub bookClub = TestDataFactory.createBookClub(member);
        bookClubRepository.save(bookClub);

        BookClubMember bookClubMember = new BookClubMember(bookClub, member);
        bookClubMemberRepository.save(bookClubMember);

        Book book = TestDataFactory.createBook(bookClub);
        bookRepository.save(book);

        Chapter chapter = TestDataFactory.createChapter(book);
        chapterRepository.save(chapter);

        Topic topic = TestDataFactory.createTopic(chapter);
        topicRepository.save(topic);

        // when
        boolean result = authorizationRepository.existsBookClubMemberByMemberIdAndTopicId(member.getId(),
                topic.getId());

        // then
        assertThat(result).isTrue();
    }

}
