package codesquad.bookkbookk.common.redis;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codesquad.bookkbookk.common.jwt.JwtProperties;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class RedisService {

    private static final String ACCESS_TOKEN_PREFIX = "access_token_blacklist_";
    private static final String REFRESH_TOKEN_PREFIX = "refresh_token_";
    private static final String INVITATION_CODE_PREFIX = "invitation_code_";

    private final RedisTemplate<String, Object> redisTemplate;
    private final JwtProperties jwtProperties;

    @Value("${invitation.expiration}")
    private long invitationCodeExpiration;

    public void saveAccessToken(String accessToken) {
        String key = ACCESS_TOKEN_PREFIX + accessToken;

        redisTemplate.opsForValue().set(key, "0", jwtProperties.getAccessTokenExpiration(),
                TimeUnit.MILLISECONDS);
    }

    public Boolean isAccessTokenPresent(String accessToken) {
        String key = ACCESS_TOKEN_PREFIX + accessToken;

        return redisTemplate.hasKey(key);
    }

    public void saveRefreshTokenUuid(String refreshTokenUuid, Long memberId) {
        String key = REFRESH_TOKEN_PREFIX + refreshTokenUuid;

        redisTemplate.opsForValue().set(key, String.valueOf(memberId), jwtProperties.getRefreshTokenExpiration(),
                TimeUnit.MILLISECONDS);
    }

    public Long getMemberIdByUuid(String uuid) {
        String key = REFRESH_TOKEN_PREFIX + uuid;
        Object result = redisTemplate.opsForValue().get(key);

        if (result == null) return null;
        return Long.valueOf((String) result);
    }

    public void deleteRefreshToken(String refreshToken) {
        String key = REFRESH_TOKEN_PREFIX + refreshToken;

        redisTemplate.delete(key);
    }

    public void saveInvitationCode(String invitationCode, Long bookClubId) {
        String key = INVITATION_CODE_PREFIX + invitationCode;

        redisTemplate.opsForValue().set(key, String.valueOf(bookClubId), invitationCodeExpiration,
                TimeUnit.MILLISECONDS);
    }

    public Long getBookClubIdByInvitationCode(String invitatioCode) {
        String key = INVITATION_CODE_PREFIX + invitatioCode;
        Object result = redisTemplate.opsForValue().get(key);

        if (result == null) return null;
        return Long.valueOf((String) result);
    }
}
