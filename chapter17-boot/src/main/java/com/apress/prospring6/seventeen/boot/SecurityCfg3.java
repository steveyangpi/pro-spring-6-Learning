package com.apress.prospring6.seventeen.boot;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity
public class SecurityCfg3 {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/styles/**","/images/**").permitAll()
                        .anyRequest().authenticated())
                //.httpBasic(Customizer.withDefaults()) // or .httpBasic
                // .logout(Customizer.withDefaults()) // or .logout()
                .logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer
                        .logoutUrl("/exit")
                        .permitAll()
                        .clearAuthentication(true))
                //.formLogin(Customizer.withDefaults())     // or formLogin()
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
    AuthenticationManagerBuilder authenticationManagerBuilder(ObjectPostProcessor<Object> objectPostProcessor, DataSource dataSource){
        var authenticationManagerBuilder = new AuthenticationManagerBuilder(objectPostProcessor);

        final String findUserQuery =
                """
                    select username,password,enabled
                    from users where username = ?
                 """;

        final String findRoles =
                """
                    select username,authority from authorities
                    where username = ?
                """;

        /*        final String findUserQuery =
                """
                    select staff_id as username,
                    staff_credentials as password,
                    active as enabled
                    from staff  where staff_id = ?
                """;
        final String findRoles =
                """
                   select staff_id as username,
                   role as authority
                   from roles where staff_id = ?
                """;*/

        try {
            authenticationManagerBuilder.jdbcAuthentication().dataSource(dataSource)
                    .passwordEncoder(encoder())
                    .usersByUsernameQuery(findUserQuery)
                    .authoritiesByUsernameQuery(findRoles);
            return authenticationManagerBuilder;
        }catch (Exception e){
            throw new RuntimeException("Could not initialize 'AuthenticationManagerBuilder'");
        }
    }
}
