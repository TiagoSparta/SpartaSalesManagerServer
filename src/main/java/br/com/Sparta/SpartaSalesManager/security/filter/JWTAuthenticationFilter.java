package br.com.Sparta.SpartaSalesManager.security.filter;


import br.com.Sparta.SpartaSalesManager.persistence.model.ApplicationUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static br.com.Sparta.SpartaSalesManager.security.filter.Constants.*;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            ApplicationUser user = new ObjectMapper().readValue(request.getInputStream(), ApplicationUser.class);
            return authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        ZonedDateTime expTimeUTC = ZonedDateTime.now(ZoneOffset.UTC).plus(EXPIRATION_TIME.getConstantTime(), ChronoUnit.MILLIS);
        String accessType = ((ApplicationUser) authResult.getPrincipal()).getAdministrador() != null ? "administrador" : "vendedor";
        String token = Jwts.builder()
                .setSubject(((ApplicationUser) authResult.getPrincipal()).getUsername())
                .setExpiration(Date.from(expTimeUTC.toInstant()))
                .claim("accessType", accessType)
                .signWith(SignatureAlgorithm.HS256, SECRET.getConstant())
                .compact();
        token = TOKEN_PREFIX.getConstant() + token;
        String tokenJson = String.format("{\"token\": %s, \"exp\": %s, \"accessType\": %s}", addQuotes(token), addQuotes(expTimeUTC.toString()), addQuotes(accessType));
        response.getWriter().write(tokenJson);
        response.addHeader("Content-Type", "application/json;charset=UTF-8");
        response.addHeader(HEADER_STRING.getConstant(), token);
    }

    private String addQuotes(String value) {
        return new StringBuilder(300).append("\"").append(value).append("\"").toString();
    }
}