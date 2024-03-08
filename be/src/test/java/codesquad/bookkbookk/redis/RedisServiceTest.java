package codesquad.bookkbookk.redis;

import static org.assertj.core.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import codesquad.bookkbookk.IntegrationTest;
import codesquad.bookkbookk.common.jwt.JwtProperties;
import codesquad.bookkbookk.common.jwt.JwtProvider;
import codesquad.bookkbookk.common.redis.RedisService;
import codesquad.bookkbookk.domain.bookclub.data.entity.BookClub;
import codesquad.bookkbookk.domain.bookclub.repository.BookClubRepository;
import codesquad.bookkbookk.domain.member.data.entity.Member;
import codesquad.bookkbookk.domain.member.repository.MemberRepository;
import codesquad.bookkbookk.util.TestDataFactory;

public class RedisServiceTest extends IntegrationTest {

    @Autowired
    private RedisService redisService;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BookClubRepository bookClubRepository;

    @Value("${invitation.expiration}")
    private long invitationCodeExpiration;

    @Test
    @DisplayName("access token을 redis에 저장한다.")
    void saveAccessToken() {
        // given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);

        String accessToken = jwtProvider.createAccessToken(member.getId());

        // when
        redisService.saveAccessToken(accessToken);

        // then
        assertThat(redisService.isAccessTokenPresent(accessToken)).isTrue();
    }

    @Test
    @DisplayName("redis에 저장된 access toekn이 만료된 그 access token을 redis에 조회하면 존재하지 않는다.")
    void expireSavedAccessToken() throws InterruptedException {
        // given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);

        String accessToken = jwtProvider.createAccessToken(member.getId());

        // when
        redisService.saveAccessToken(accessToken);
        Thread.sleep(jwtProperties.getAccessTokenExpiration() + 1000);

        // then
        assertThat(redisService.isAccessTokenPresent(accessToken)).isFalse();
    }

    @Test
    @DisplayName("redis에 refreshToken을 저장한다.")
    void saveRefreshToken() {
        // given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);

        String refreshToken = jwtProvider.createRefreshToken();

        // when
        redisService.saveRefreshToken(refreshToken, member.getId());

        // then
        assertThat(redisService.getMemberIdByRefreshToken(refreshToken)).isEqualTo(member.getId());
    }

    @Test
    @DisplayName("redis에 저장된 refresh toekn이 만료된 뒤 그 refresh token을 redis에 조회하면 존재하지 않는다.")
    void expireSavedRefreshToken() throws InterruptedException {
        // given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);

        String refreshToken = jwtProvider.createRefreshToken();

        // when
        redisService.saveRefreshToken(refreshToken, member.getId());
        Thread.sleep(jwtProperties.getRefreshTokenExpiration() + 1000);

        // then
        assertThat(redisService.getMemberIdByRefreshToken(refreshToken)).isNull();
    }

    @Test
    @DisplayName("redis에 invitation code를 저장한다.")
    void saveInvitationCode() {
        // given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);

        BookClub bookClub = TestDataFactory.createBookClub(member);
        bookClubRepository.save(bookClub);

        String invitationCode = String.valueOf(UUID.randomUUID());

        // when
        redisService.saveRefreshToken(invitationCode, bookClub.getId());

        // then
        assertThat(redisService.getMemberIdByRefreshToken(invitationCode)).isEqualTo(bookClub.getId());
    }

    @Test
    @DisplayName("redis에 저장된 invitation code가 만료된 뒤 그 invitation code를 redis에 조회하면 존재하지 않는다.")
    void expireSavedInvitationCode() throws InterruptedException {
        // given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);

        BookClub bookClub = TestDataFactory.createBookClub(member);
        bookClubRepository.save(bookClub);

        String invitationCode = String.valueOf(UUID.randomUUID());

        // when
        redisService.saveRefreshToken(invitationCode, bookClub.getId());
        Thread.sleep(invitationCodeExpiration + 1000);

        // then
        assertThat(redisService.getBookClubIdByInvitationCode(invitationCode)).isNull();
    }

}
