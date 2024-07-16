package com.apress.prospring6.four.profile.kindergarten;

import com.apress.prospring6.four.profile.FoodProviderService;
import com.apress.prospring6.four.profile.highschool.FoodProviderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("kindergarten")
public class KindergartenConfig {

    @Bean
    FoodProviderService foodProviderService() {
        return new FoodProviderServiceImpl();
    }
}
