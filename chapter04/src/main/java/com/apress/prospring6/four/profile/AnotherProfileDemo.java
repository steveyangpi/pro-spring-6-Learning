package com.apress.prospring6.four.profile;

import com.apress.prospring6.four.profile.highschool.HighSchoolConfig;
import com.apress.prospring6.four.profile.kindergarten.KindergartenConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AnotherProfileDemo {
    private static Logger LOGGER = LoggerFactory.getLogger(AnotherProfileDemo.class);

    public static void main(String[] args) {
        var ctx = new AnnotationConfigApplicationContext(HighSchoolConfig.class, KindergartenConfig.class);

        var foodProviderService = ctx.getBean("foodProviderService",FoodProviderService.class);
        var lunchSet = foodProviderService.provideLunchSet();
        lunchSet.forEach(food -> LOGGER.info("Food: {}",food.getName()));
        ctx.close();
    }
}
