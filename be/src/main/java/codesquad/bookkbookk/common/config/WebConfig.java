package codesquad.bookkbookk.common.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import codesquad.bookkbookk.common.resolver.AccessTokenArgumentResolver;
import codesquad.bookkbookk.common.resolver.JwtArgumentResolver;
import codesquad.bookkbookk.common.resolver.RefreshTokenUuidArgumentResolver;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private JwtArgumentResolver jwtArgumentResolver;
    @Autowired
    private RefreshTokenUuidArgumentResolver refreshTokenUuidArgumentResolver;
    @Autowired
    private AccessTokenArgumentResolver accessTokenArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(jwtArgumentResolver);
        resolvers.add(refreshTokenUuidArgumentResolver);
        resolvers.add(accessTokenArgumentResolver);
    }

}
