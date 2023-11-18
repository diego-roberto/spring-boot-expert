package io.github.diegoroberto.security.jwt;

import io.github.diegoroberto.domain.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class JwtService {

    @Value("${security.jwt.expire}")
    private String expire;

    @Value("${security.jwt.key-sign}")
    private String keySign;

    public String generateToken(User user) {
        long expString = Long.parseLong(expire);
        LocalDateTime expDateTime = LocalDateTime.now().plusMinutes(expString);
        Instant instant = expDateTime.atZone(ZoneId.systemDefault()).toInstant();
        Date expDate = Date.from(instant);

        return Jwts
                .builder()
                .setSubject(user.getLogin())
                .setExpiration(expDate)
                .signWith(SignatureAlgorithm.HS512, keySign)
                .compact();

    }

    private Claims getClaims(String token) throws ExpiredJwtException {
        return Jwts
                .parser()
                .setSigningKey(keySign)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isValidExpirableToken(String token){
        try {
            Claims claims = getClaims(token);
            Date expDate = claims.getExpiration();

            LocalDateTime expDatetime = expDate
                                            .toInstant()
                                            .atZone(ZoneId.systemDefault()).toLocalDateTime();

            return !LocalDateTime.now().isAfter(expDatetime);

        } catch (Exception ex) {
            return false;
        }
    }

    public String getUserLogin(String token) throws ExpiredJwtException{
        return getClaims(token).getSubject();
    }

}
