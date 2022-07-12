package com.example.chuyendeweb.config;

import com.example.chuyendeweb.filter.JwtRequestFilter;
import com.example.chuyendeweb.security.JwtAuthenticationEntryPoint;
import com.example.chuyendeweb.security.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        // securedEnabled = true,
        // jsr250Enabled = true,
        prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    JwtUserDetailsService userDetailsService;
    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Bean
    public JwtRequestFilter authenticationJwtTokenFilter() {
        return new JwtRequestFilter();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
//                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests().antMatchers("/api/auth/**").permitAll()
                .antMatchers("/api/test/**").permitAll()
//                .antMatchers("/api/cart/**").permitAll()
                .antMatchers("/api/product/**").permitAll()
                .antMatchers("/api/category/**").permitAll()
                .antMatchers("/api/wishlist/**").permitAll()
                .antMatchers("/api/order/**").permitAll()
                .antMatchers("/api/payment/**").permitAll()
//                .antMatchers("/api/admin/**").hasRole("ADMIN")
//                .antMatchers("/api/cart/**").hasRole("USER")
//                .antMatchers("/api/mod/**").hasRole("MODERATOR")
               .antMatchers("api/manage/admin/**").hasRole("ADMIN")
               .antMatchers("api/order/admin/**").hasRole("ADMIN")
               .antMatchers("api/product/admin/**").hasRole("ADMIN")
                .antMatchers("api/chart/admin/**").hasRole("ADMIN")
               .antMatchers("/sell/admin/**").hasRole("ADMIN")

                .anyRequest().authenticated().and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
