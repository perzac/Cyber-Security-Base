/*
 *  pexak
 *  Cyber Security Base - Course Project I
 *
 *  Course given by University of Helsinki in collaboration with F-Secure 
 *
 */

package sec.project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    /*    http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS);*/
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/form").permitAll()
                .antMatchers("/form/signedup").permitAll()
                .antMatchers("/done").permitAll()
                .antMatchers("/info").permitAll()
                .antMatchers("/feelings").permitAll()
                .antMatchers("/event").permitAll()
                .antMatchers("/people").permitAll()
                .antMatchers("/people/*").permitAll()
                .antMatchers("/feedback").access("hasAuthority('USER')")
                .antMatchers("/admin").access("hasAuthority('ADMIN')")
                .anyRequest().authenticated().and()
                .formLogin().permitAll();
        http.logout().logoutUrl("/logout").logoutSuccessUrl("/event").deleteCookies("JSESSIONID");             ;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
