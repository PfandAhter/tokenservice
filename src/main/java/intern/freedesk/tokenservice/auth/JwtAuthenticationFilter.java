package intern.freedesk.tokenservice.auth;

import intern.freedesk.tokenservice.rest.service.impl.HeaderServiceImpl;
import intern.freedesk.tokenservice.rest.service.impl.TokenBlackListServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
@WebFilter(filterName = "RequestCachingFilter" , urlPatterns = "/")
//@WebFilter(filterName = "RequestCachingFilter" , urlPatterns = "/??")

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final TokenBlackListServiceImpl tokenBlackList;

    private final HeaderServiceImpl headerService;

    //https://www.baeldung.com/spring-exclude-filter
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        request.isRequestedSessionIdFromURL();
        return request.getRequestURL().substring(0,21).equals("http://localhost:8079");
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        String jwt = "";
        final String username;

        if ((authHeader == null || !authHeader.startsWith("Bearer ")) && !tokenBlackList.isBlackListed(headerService.getHeader(request).getToken()) ) {
            filterChain.doFilter(request, response);
            return;
        }else{
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }

        jwt = authHeader.split(" ")[1];
        jwt = jwtService.decryptJwt(jwt);

        try {
            username = jwtService.extractUsername(jwt);
        } catch (ExpiredJwtException e) {
            logger.error("Jwt expired");
            filterChain.doFilter(request, response);
            return;
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
