package uoc.edu.easyorderbackend.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.filter.OncePerRequestFilter;
import uoc.edu.easyorderbackend.model.FirebaseAuthenticationToken;
import uoc.edu.easyorderbackend.provider.FirebaseIdTokenAuthenticationProvider;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FirebaseIdTokenFilter extends OncePerRequestFilter {

    private final static Logger logger = LoggerFactory.getLogger(FirebaseIdTokenFilter.class);

    private final AuthenticationEntryPoint entryPoint;
    private final FirebaseIdTokenAuthenticationProvider authenticationProvider;

    public FirebaseIdTokenFilter(AuthenticationEntryPoint entryPoint, FirebaseIdTokenAuthenticationProvider authenticationProvider) {

        if (authenticationProvider == null) {
            throw new IllegalArgumentException("Authentication Provider must not be null");
        }
        if (entryPoint == null) {
            throw new IllegalArgumentException("Entry Point must not be null");
        }
        this.entryPoint = entryPoint;
        this.authenticationProvider = authenticationProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {

            logger.info("FirebaseIdTokenFilter: Filtering");

            String authorization = request.getHeader("Authorization");

            if (authorization != null) {
                String idToken = authorization.replace("Bearer ", "");
                FirebaseAuthenticationToken authenticationToken = new FirebaseAuthenticationToken(idToken);
                //authenticationProvider.validateToken(authenticationToken);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
            filterChain.doFilter(request, response);
        } catch (AuthenticationException authenticationException) {
            // Exception thrown by validateToken if token is not valid
            logger.error("FirebaseIdTokenFilter: {}", authenticationException.getMessage());
            SecurityContextHolder.clearContext();
            entryPoint.commence(request, response, authenticationException);
        }
    }
}
