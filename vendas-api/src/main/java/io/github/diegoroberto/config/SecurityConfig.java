package io.github.diegoroberto.config;

import io.github.diegoroberto.constants.RoleConstants;
import io.github.diegoroberto.security.jwt.JwtAuthFilter;
import io.github.diegoroberto.security.jwt.JwtService;
import io.github.diegoroberto.service.implement.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private JwtService jwtService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public OncePerRequestFilter jwtFilter() {
        return new JwtAuthFilter(jwtService, userService);
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
            .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/users/**").permitAll()
                .antMatchers("/api/products/**").hasRole(RoleConstants.ADMIN)
                .antMatchers("/api/users/all").hasRole(RoleConstants.ADMIN)
                .antMatchers("/api/clients/all").hasRole(RoleConstants.ADMIN)
                .antMatchers("/api/clients/**").hasAnyRole(RoleConstants.USER, RoleConstants.ADMIN)
                .antMatchers("/api/orders/**").hasAnyRole(RoleConstants.USER, RoleConstants.ADMIN)
            .anyRequest().authenticated()
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);


            /* incluir form de login customizado (Basic auth)*/

            //       .authorizeRequests()
            //       .antMatchers("...")
            //       .formLogin() /* form default do spring */
            //       .formLogin(/login-page.html") /* em resources/templates */

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");

    }
}
