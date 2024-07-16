package com.apress.prospring6.four;

import jakarta.annotation.PostConstruct;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.support.ResourcePropertySource;

class AppProperty {
    private String applicationHome;
    private String userHome;

    public String getApplicationHome() {
        return applicationHome;
    }

    @Autowired
    public void setApplicationHome(@Value("application_home") String applicationHome) {
        this.applicationHome = applicationHome;
    }

    public String getUserHome() {
        return userHome;
    }

    @Autowired
    public void setUserHome(@Value("/home/CUSTOM-USER-HOME") String userHome) {
        this.userHome = userHome;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("applicationHome", applicationHome)
                .append("userHome", userHome)
                .toString();
    }
}

@Configuration
@PropertySource("classpath:application.properties")
class PropDemoConfig {

    @Autowired
    StandardEnvironment environment;

    @PostConstruct
    void configPriority(){
        ResourcePropertySource rps = (ResourcePropertySource) environment.getPropertySources().stream().filter(ps -> ps instanceof ResourcePropertySource).findAny().orElse(null);
        environment.getPropertySources().addFirst(rps);
    }

    @Bean
    AppProperty appProperty(){
        return new AppProperty();
    }
}

public class PropertySourceDemo {
    private static Logger logger = LoggerFactory.getLogger(PropertySourceDemo.class);

    public static void main(String[] args) {
        var ctx = new AnnotationConfigApplicationContext(PropDemoConfig.class);

        var appProperty = ctx.getBean("appProperty",AppProperty.class);
        logger.info("Outcome: {}",appProperty);
    }
}
