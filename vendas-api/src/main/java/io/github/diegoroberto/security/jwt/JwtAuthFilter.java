package io.github.diegoroberto.security.jwt;

import io.github.diegoroberto.service.implement.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public final UserServiceImpl userService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        String authorization = httpServletRequest.getHeader("Authorization");

        if( authorization != null && authorization.startsWith("Bearer")){
            String token = authorization.split(" ")[1];
            boolean isValid = jwtService.isValidExpirableToken(token);

            if(isValid){
                String userLogin = jwtService.getUserLogin(token);
                UserDetails user = userService.loadUserByUsername(userLogin);

                UsernamePasswordAuthenticationToken userAuthCredentials =
                        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

                userAuthCredentials.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(httpServletRequest)
                );

                SecurityContextHolder.getContext().setAuthentication(userAuthCredentials);
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);

        }
}
