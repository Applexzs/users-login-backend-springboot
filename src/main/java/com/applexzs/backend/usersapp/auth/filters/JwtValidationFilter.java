package com.applexzs.backend.usersapp.auth.filters;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.applexzs.backend.usersapp.auth.TokenJwtConfig.*;

public class JwtValidationFilter extends BasicAuthenticationFilter {


    public JwtValidationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(HEADER_AUTHORIZATION);
        if (header == null || !header.startsWith(PREFIX_TOKEN)) {
            chain.doFilter(request, response);
            return;
        }

        String token = header.replace(PREFIX_TOKEN, "");


        try {
            Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody();


            Object authoritiesClaims = claims.get("authorities");
            String username = claims.getSubject();
            Object username2 = claims.get("username");
            System.out.println("username1 = " + username);
            System.out.println("username2 = " + username2);

            List<Map<String, String>> rawAuthorities = new ObjectMapper()
                    .readValue(authoritiesClaims.toString(), new TypeReference<List<Map<String, String>>>() {});

            Collection<? extends GrantedAuthority> authorities = rawAuthorities.stream()
                    .map(map -> new SimpleGrantedAuthority(map.get("authority")))
                    .collect(Collectors.toList());

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);
        } catch (JwtException e) {
            Map<String, String> body = new HashMap<>();
            body.put("error", e.getMessage());
            body.put("message", "El token no es valido");

            response.getWriter().write(new ObjectMapper().writeValueAsString(body));
            response.setStatus(401);
            response.setContentType("application/json");
        }


    }
}
