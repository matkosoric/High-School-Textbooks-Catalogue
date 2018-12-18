package edu.matko.soric.textbooks.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final static String REALM = "TextbookCatalogue";

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.
            inMemoryAuthentication()
                .withUser("user").password(passwordEncoder().encode("user")).roles("USER").and()
                .withUser("admin").password(passwordEncoder().encode("admin")).roles("USER","ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/**/textbooks/new/**").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/**/textbooks/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/**/textbooks/**").permitAll()
                .and().httpBasic().realmName(REALM).authenticationEntryPoint(getBasicAuthEntryPoint())
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().headers().frameOptions().sameOrigin()
                .and().csrf().disable();
    }

    @Bean
    public CustomBasicAuthenticationEntryPoint getBasicAuthEntryPoint() {
        return new CustomBasicAuthenticationEntryPoint();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}