package smartyflip.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import smartyflip.security.service.TokenService;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    private final UserDetailsService userDetailsService;
    private final TokenService tokenService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String refreshToken = getAccessTokenFromRequest(request, "refresh-token");

        if (refreshToken != null && tokenService.isRefreshTokenValid(refreshToken)) {

            String accessToken = getAccessTokenFromRequest(request, "access-token");

            UserDetails userDetails = null;

            if (accessToken != null && tokenService.isAccessTokenValid(accessToken)) {
                userDetails = userDetailsService.loadUserByUsername(tokenService.getUsernameFromToken(accessToken, "ACCESS"));
            } else {
                userDetails = userDetailsService.loadUserByUsername(tokenService.getUsernameFromToken(refreshToken, "REFRESH"));
                String newAccessToken = tokenService.generateAccessToken(userDetails);
                Cookie cookie = new Cookie("access-token", newAccessToken);
                cookie.setPath("/");
                cookie.setHttpOnly(true);
                response.addCookie(cookie);
            }

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
                    null,
                    userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String getAccessTokenFromRequest(HttpServletRequest request, String tokenName) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (tokenName.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }
}
