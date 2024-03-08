package codesquad.bookkbookk.common.redis;

import java.util.concurrent.TimeUnit;

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

    private final RedisTemplate<String, Object> redisTemplate;
    private final JwtProperties jwtProperties;

    public void saveAccessToken(String accessToken) {
        String key = ACCESS_TOKEN_PREFIX + accessToken;

        redisTemplate.opsForValue().set(key, "0", jwtProperties.getAccessTokenExpiration(),
                TimeUnit.MILLISECONDS);
    }

    public Boolean isAccessTokenPresent(String accessToken) {
        String key = ACCESS_TOKEN_PREFIX + accessToken;

        return redisTemplate.hasKey(key);
    }

}
