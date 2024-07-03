package codesquad.bookkbookk.integration.scenario;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import codesquad.bookkbookk.common.error.exception.BookClubNotFoundException;
import codesquad.bookkbookk.common.error.exception.MemberNotFoundException;
import codesquad.bookkbookk.common.error.exception.MemberNotInBookClubException;
import codesquad.bookkbookk.domain.auth.service.AuthorizationService;
import codesquad.bookkbookk.domain.bookclub.data.entity.BookClub;
import codesquad.bookkbookk.domain.bookclub.repository.BookClubRepository;
import codesquad.bookkbookk.domain.mapping.entity.BookClubMember;
import codesquad.bookkbookk.domain.mapping.repository.BookClubMemberRepository;
import codesquad.bookkbookk.domain.member.data.entity.Member;
import codesquad.bookkbookk.domain.member.repository.MemberRepository;
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
        ).isInstanceOf(BookClubNotFoundException.class);
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
        ).isInstanceOf(MemberNotFoundException.class);
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

}
