package codesquad.bookkbookk.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import codesquad.bookkbookk.filter.CorsFilter;
import codesquad.bookkbookk.filter.JwtFilter;
import codesquad.bookkbookk.jwt.JwtProvider;

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
    public FilterRegistrationBean<JwtFilter> jwtFilter(JwtProvider provider) {
        FilterRegistrationBean<JwtFilter> filterRegistrationBean = new FilterRegistrationBean<>();

        filterRegistrationBean.setFilter(new JwtFilter(provider));
        filterRegistrationBean.setOrder(2);
        return filterRegistrationBean;
    }

}
