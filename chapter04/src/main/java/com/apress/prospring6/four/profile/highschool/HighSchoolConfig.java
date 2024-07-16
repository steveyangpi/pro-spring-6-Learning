package com.apress.prospring6.four.profile.highschool;

import com.apress.prospring6.four.profile.FoodProviderService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("highschool")
public class HighSchoolConfig {

    @Bean
    FoodProviderService foodProviderService(){
        return new FoodProviderServiceImpl();
    }
}
