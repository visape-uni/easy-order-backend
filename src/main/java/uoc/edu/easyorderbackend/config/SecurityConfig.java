package uoc.edu.easyorderbackend.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import uoc.edu.easyorderbackend.constants.UrlEasyOrderConstants;
import uoc.edu.easyorderbackend.filter.FirebaseIdTokenFilter;
import uoc.edu.easyorderbackend.handler.RestAuthenticationEntryPoint;
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

        // AuthorizeRequests makes apiUrl to check method authenticate from Provider
        http.addFilterBefore(new FirebaseIdTokenFilter(authenticationEntryPoint(), authenticationProvider), BasicAuthenticationFilter.class)
                .csrf().disable()
                .authorizeRequests()
                //.antMatchers(UrlEasyOrderConstants.apiUrl+"/**") // ** Makes that all the urls that start with API must be authenticated
                //.authenticated()
                .antMatchers(UrlEasyOrderConstants.userUrl+UrlEasyOrderConstants.getUrl)
                .authenticated()
                .antMatchers(UrlEasyOrderConstants.userUrl+UrlEasyOrderConstants.createUrl).permitAll()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().exceptionHandling().authenticationEntryPoint(authenticationEntryPoint());

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        logger.info("SecurityConfig: Configuring auth");
        auth.authenticationProvider(authenticationProvider);
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }
}
