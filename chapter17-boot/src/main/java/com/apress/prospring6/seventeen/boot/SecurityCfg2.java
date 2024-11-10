package com.apress.prospring6.seventeen.boot;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

/*@Configuration
@EnableWebSecurity
@EnableMethodSecurity*/
public class SecurityCfg2 {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers( "/styles/**", "/images/**").permitAll()
                        .anyRequest().authenticated())
                //.httpBasic(Customizer.withDefaults()) // or .httpBasic
                // .logout(Customizer.withDefaults()) // or .logout()
                .logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer
                        .logoutUrl("/exit")
                        .permitAll()
                        .clearAuthentication(true))
                //.formLogin(Customizer.withDefaults())   // or .formLogin()
                .formLogin(loginConfigurer -> loginConfigurer
                        .loginPage("/auth")
                        .loginProcessingUrl("/auth")
                        .usernameParameter("user")
                        .passwordParameter("pass")
                        .defaultSuccessUrl("/home")
                        .permitAll())
                //.csrf().disable(); // deprecated starting with Spring 6.1
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    @Bean
    public PasswordEncoder encoder(){return new BCryptPasswordEncoder();}

    @Bean
    UserDetailsManager users(DataSource dataSource){
        User.UserBuilder users = User.builder().passwordEncoder(encoder()::encode);
        var joe = users
                .username("joe")
                .password("doe")
                .roles("USER")
                .build();
        var jane = users
                .username("jane")
                .password("doe")
                .roles("USER","ADMIN")
                .build();
        var admin = users
                .username("admin")
                .password("admin")
                .roles("ADMIN")
                .build();

        var manager = new JdbcUserDetailsManager(dataSource);
        manager.createUser(joe);
        manager.createUser(jane);
        manager.createUser(admin);
        return manager;
    }
}
