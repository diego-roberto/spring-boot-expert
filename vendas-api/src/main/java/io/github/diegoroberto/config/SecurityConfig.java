package io.github.diegoroberto.config;

import io.github.diegoroberto.service.implement.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserServiceImpl userService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
            .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                    .antMatchers("/api/clients/**")
                        .hasAnyRole("USER", "ADMIN")
                    .antMatchers("/api/orders/**")
                        .hasAnyRole("USER", "ADMIN")
                    .antMatchers("/api/products/**")
                        .hasRole("ADMIN")
                    .antMatchers(HttpMethod.POST, "/api/users/**")
                     .permitAll()
                .anyRequest().authenticated()
                .and()
                .anonymous().disable();


            /* incluir form de login customizado */

//                .authorizeRequests()
//                .antMatchers("...")
//                .formLogin() /* form default do spring */
//                .formLogin(/login-page.html") /* em resources/templates */

    }
}
