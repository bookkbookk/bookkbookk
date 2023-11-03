package codesquad.bookkbookk.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import codesquad.bookkbookk.common.filter.CorsFilter;
import codesquad.bookkbookk.common.filter.JwtFilter;
import codesquad.bookkbookk.common.jwt.JwtProvider;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter(@Value("${cors.allowed-origin}") String allowedOrigin) {
        FilterRegistrationBean<CorsFilter> filterRegistrationBean = new FilterRegistrationBean<>();

        filterRegistrationBean.setFilter(new CorsFilter(allowedOrigin));
        filterRegistrationBean.setOrder(1);
        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean<JwtFilter> jwtFilter(JwtProvider provider, ObjectMapper objectMapper) {
        FilterRegistrationBean<JwtFilter> filterRegistrationBean = new FilterRegistrationBean<>();

        filterRegistrationBean.setFilter(new JwtFilter(provider, objectMapper));
        filterRegistrationBean.setOrder(2);
        return filterRegistrationBean;
    }

}
