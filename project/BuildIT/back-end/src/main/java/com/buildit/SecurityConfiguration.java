package com.buildit;

import com.buildit.procurement.domain.enums.Role;
import org.eclipse.jetty.http.HttpMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;

import javax.sql.DataSource;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled=true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    void authentication(AuthenticationManagerBuilder auth) throws Exception {
        User.UserBuilder userBuilder = User.withDefaultPasswordEncoder();
        auth.jdbcAuthentication().dataSource(dataSource).withDefaultSchema()
                .withUser(userBuilder.username("user1").password("user1").roles("WORK_ENGINEER"))
                .and()
                .inMemoryAuthentication()
                .withUser(userBuilder.username("user2").password("user2").roles("SITE_ENGINEER"));
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().and()
                .authorizeRequests()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/sayHello").hasAnyRole("USER1")
                .antMatchers("/api/**").authenticated()
                .and().httpBasic();
        http.headers().frameOptions().disable();
    }
}
