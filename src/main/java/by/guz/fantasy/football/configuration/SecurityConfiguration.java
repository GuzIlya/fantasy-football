package by.guz.fantasy.football.configuration;

import by.guz.fantasy.football.security.TokenAuthenticationProvider;
import by.guz.fantasy.football.security.TokenFilter;
import by.guz.fantasy.football.security.TokenValidator;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

import static by.guz.fantasy.football.api.AuthApiController.*;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@Import(SecurityProblemSupport.class)
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final TokenValidator tokenValidator;
    private final SecurityProblemSupport problemSupport;
    private final TokenAuthenticationProvider tokenAuthenticationProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf()
                .disable();
        httpSecurity
                .cors();
        httpSecurity
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        httpSecurity
                .addFilterBefore(new TokenFilter(tokenValidator), BasicAuthenticationFilter.class);
        httpSecurity
                .authenticationProvider(tokenAuthenticationProvider);
        httpSecurity.exceptionHandling()
                .authenticationEntryPoint(problemSupport)
                .accessDeniedHandler(problemSupport);
        httpSecurity.authorizeRequests()
                .antMatchers(HttpMethod.POST, LOGIN_PATH).permitAll()
                .antMatchers(HttpMethod.POST, SIGNUP_PATH).permitAll()
                .antMatchers(HttpMethod.POST, REFRESH_TOKEN_PATH).permitAll()
                .antMatchers("/api/v1/**").authenticated();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(tokenAuthenticationProvider);
    }
}
