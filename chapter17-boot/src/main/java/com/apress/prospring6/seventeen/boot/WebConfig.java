package com.apress.prospring6.seventeen.boot;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.mvc.WebContentInterceptor;
import org.springframework.web.servlet.theme.CookieThemeResolver;
import org.springframework.web.servlet.theme.ThemeChangeInterceptor;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Locale;

@Configuration
@ComponentScan(basePackages = {"com.apress.prospring6.seventeen.boot"})
public class WebConfig implements WebMvcConfigurer {

    @Bean
    MessageSource messageSource(){
        var messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:i18n/global");
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setFallbackToSystemLocale(true);
        // # -1 : never reload, 0 always reload
        //messageResource.setCacheSeconds(0)
        return messageSource;
    }

    @Bean
    LocaleChangeInterceptor localeChangeInterceptor(){
        var localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        return localeChangeInterceptor;
    }

    // Deprecated as of 6.0 in favor of using CSS,without direct replacement
    @SuppressWarnings("deprecation")
    @Bean
    ThemeChangeInterceptor themeChangeInterceptor(){
        var themeChangeInterceptor = new ThemeChangeInterceptor();
        themeChangeInterceptor.setParamName("theme");
        return themeChangeInterceptor;
    }

    @Bean
    CookieLocaleResolver localeResolver(){
        var cookieLocaleResolver = new CookieLocaleResolver("locale");
        cookieLocaleResolver.setDefaultLocale(Locale.ENGLISH);
        //cookieLocaleResolver.setCookieMaxAge(3600);  // deprecated of 6.0
        cookieLocaleResolver.setCookieMaxAge(Duration.ofSeconds(3600L));
        //cookieLocaleResolver.setCookieName("locale");   // deprecated of 6.0, argument added
        return cookieLocaleResolver;
    }

    // Deprecated as of 6.0 in favor of using CSS, without direct replacement
    @SuppressWarnings("deprecation")
    @Bean
    CookieThemeResolver themeResolver(){
        var cookieThemeResolver = new CookieThemeResolver();
        cookieThemeResolver.setDefaultThemeName("green");
        cookieThemeResolver.setCookieMaxAge(3600);
        cookieThemeResolver.setCookieName("theme");
        return cookieThemeResolver;
    }

    @Bean
    WebContentInterceptor webChangeInterceptor(){
        var webContentInterceptor = new WebContentInterceptor();
        webContentInterceptor.setCacheSeconds(0);
        webContentInterceptor.setSupportedMethods("GET","POST","PUT","DELETE");
        return webContentInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor()).addPathPatterns("/*");
        registry.addInterceptor(themeChangeInterceptor());
        registry.addInterceptor(webChangeInterceptor());
    }
}
