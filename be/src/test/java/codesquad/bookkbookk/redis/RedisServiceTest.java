package codesquad.bookkbookk.redis;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import codesquad.bookkbookk.IntegrationTest;
import codesquad.bookkbookk.common.jwt.JwtProperties;
import codesquad.bookkbookk.common.jwt.JwtProvider;
import codesquad.bookkbookk.common.redis.RedisService;
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
    void saveRefreshToken() throws InterruptedException {
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

}
