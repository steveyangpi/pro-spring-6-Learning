package com.apress.prospring6.seventeen;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/*@Configuration
@EnableWebSecurity
@EnableMethodSecurity*/
public class SecurityCfg {


    @Bean
    public SecurityFilterChain securityWebFilterChain(HttpSecurity http) throws Exception{
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/styles/**","/images/**").permitAll()
                        .anyRequest().authenticated())
//                .httpBasic(Customizer.withDefaults())
//                .logout(Customizer.withDefaults())
                .logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer
                        .logoutUrl("/exit")
                        .permitAll()
                        .clearAuthentication(true))
                //.formLogin(Customizer.withDefaults())
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
    public PasswordEncoder encoder() {return new BCryptPasswordEncoder();}

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder){
        User.UserBuilder users = User.builder().passwordEncoder(encoder::encode);
        var joe = users
                .username("john")
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
        return new InMemoryUserDetailsManager(joe,jane,admin);
    }
}
