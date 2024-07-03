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
import codesquad.bookkbookk.domain.bookclub.data.entity.BookClub;
import codesquad.bookkbookk.domain.bookclub.repository.BookClubRepository;
import codesquad.bookkbookk.domain.mapping.entity.BookClubMember;
import codesquad.bookkbookk.domain.mapping.repository.BookClubMemberRepository;
import codesquad.bookkbookk.domain.member.data.entity.Member;
import codesquad.bookkbookk.domain.member.repository.MemberRepository;
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
                .findBookClubMemberAuthsByMemberIdAndBookClubId(bookClub.getId(), member.getId());

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
                .findBookClubMemberAuthsByMemberIdAndBookClubId(bookClub.getId(), member.getId());

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
                .findBookClubMemberAuthsByMemberIdAndBookClubId(bookClub.getId(), member.getId() + 1);

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
                .findBookClubMemberAuthsByMemberIdAndBookClubId(1L, member.getId());

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
                .findBookClubMemberAuthsByMemberIdAndBookClubId(bookClubs.get(2).getId(), member.getId());

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
                .findBookClubMemberAuthsByMemberIdAndBookClubId(bookClub.getId(), members.get(2).getId());

        // then
        assertThat(authInfos).hasSize(3);
    }

}
