package com.apress.prospring6.fourteen.boot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.mvc.WebContentInterceptor;
import org.springframework.web.servlet.theme.CookieThemeResolver;
import org.springframework.web.servlet.theme.ThemeChangeInterceptor;

import java.util.Locale;

@Configuration
@ComponentScan(basePackages = {"com.apress.prospring6.fourteen.boot"})
public class WebConfig implements WebMvcConfigurer {
    @Bean
    LocaleChangeInterceptor localeChangeInterceptor(){
        var localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        return localeChangeInterceptor;
    }

    @Bean
    ThemeChangeInterceptor themeChangeInterceptor(){
        var themeChangeInterceptor = new ThemeChangeInterceptor();
        themeChangeInterceptor.setParamName("theme");
        return themeChangeInterceptor;
    }

    @Bean
    CookieLocaleResolver localeResolver(){
        var cookieLocaleResolver = new CookieLocaleResolver();
        cookieLocaleResolver.setDefaultLocale(Locale.ENGLISH);
        cookieLocaleResolver.setCookieMaxAge(3600);
        cookieLocaleResolver.setCookieName("locale");
        return cookieLocaleResolver;
    }

    @Bean
    CookieThemeResolver themeResolver(){
        var cookieThemeResolver = new CookieThemeResolver();
        cookieThemeResolver.setCookieName("green");
        cookieThemeResolver.setCookieMaxAge(3600);
        cookieThemeResolver.setCookieName("theme");
        return cookieThemeResolver;
    }

    @Bean
    WebContentInterceptor webContentInterceptor(){
        var webContentInterceptor = new WebContentInterceptor();
        webContentInterceptor.setCacheSeconds(0);
        webContentInterceptor.setSupportedMethods("GET","POST","PUT","DELETE");
        return webContentInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor()).addPathPatterns("/*");
        registry.addInterceptor(themeChangeInterceptor());
        registry.addInterceptor(webContentInterceptor());
    }
}
