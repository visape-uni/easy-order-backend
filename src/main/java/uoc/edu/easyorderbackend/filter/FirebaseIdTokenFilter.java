package uoc.edu.easyorderbackend.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import uoc.edu.easyorderbackend.model.FirebaseAuthenticationToken;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FirebaseIdTokenFilter extends OncePerRequestFilter {

    private final static Logger logger = LoggerFactory.getLogger(FirebaseIdTokenFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        logger.info("FirebaseIdTokenFilter: Filtering");

        String authorization = request.getHeader("Authorization");

        if (authorization != null) {
            String idToken = authorization.replace("Bearer ", "");
            SecurityContextHolder.getContext().setAuthentication(new FirebaseAuthenticationToken(idToken));

            filterChain.doFilter(request, response);
        }
    }
}
