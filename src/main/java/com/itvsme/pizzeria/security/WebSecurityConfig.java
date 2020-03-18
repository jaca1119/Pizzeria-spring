package com.itvsme.pizzeria.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http
                .authorizeRequests()
                    .antMatchers(HttpMethod.GET,"/standard/**", "/addons/**", "/sizes").permitAll()
                    .antMatchers(HttpMethod.POST, "/order-pizza-cart").permitAll()
                    .antMatchers(HttpMethod.OPTIONS, "/order-pizza-cart").permitAll()
                    .antMatchers("/all-orders").hasRole("ADMIN")
                    .anyRequest().hasRole("ADMIN")
                    .and()
                .csrf().disable();
    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService()
    {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        UserDetails user =
                User.builder()
                    .username("qwe")
                    .password(encoder.encode("123"))
                    .roles("USER")
                    .build();
        UserDetails admin =
                User.builder()
                        .roles("USER", "ADMIN")
                        .username("admin")
                        .password(encoder.encode("admin"))
                        .build();

        return new InMemoryUserDetailsManager(user, admin);
    }
}
