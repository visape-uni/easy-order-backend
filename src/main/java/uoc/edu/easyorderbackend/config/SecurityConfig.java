package uoc.edu.easyorderbackend.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import uoc.edu.easyorderbackend.constants.UrlEasyOrderConstants;
import uoc.edu.easyorderbackend.filter.FirebaseIdTokenFilter;
import uoc.edu.easyorderbackend.provider.FirebaseIdTokenAuthenticationProvider;

@EnableWebSecurity
@Component
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final static Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    private final FirebaseIdTokenAuthenticationProvider authenticationProvider;

    @Autowired
    public SecurityConfig(FirebaseIdTokenAuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        logger.info("SecurityConfig: Configuring http");
        http.addFilterBefore(new FirebaseIdTokenFilter(), BasicAuthenticationFilter.class)
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(UrlEasyOrderConstants.apiUrl)
                .authenticated();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        logger.info("SecurityConfig: Configuring auth");
        auth.authenticationProvider(authenticationProvider);
    }

}
