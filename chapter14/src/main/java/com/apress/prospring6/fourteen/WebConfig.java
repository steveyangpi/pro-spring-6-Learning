package com.apress.prospring6.fourteen;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.theme.CookieThemeResolver;
import org.springframework.web.servlet.theme.ThemeChangeInterceptor;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.apress.prospring6.fourteen"})
public class WebConfig implements WebMvcConfigurer, ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    @Description("Thymeleaf Template Resolver")
    public SpringResourceTemplateResolver templateResolver(){
        var resolver = new SpringResourceTemplateResolver();
        resolver.setApplicationContext(this.applicationContext);
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setCacheable(false);
        return resolver;
    }

    @Bean
    @Description("Thymeleaf Template Engine")
    public SpringTemplateEngine templateEngine(){
        var engine = new SpringTemplateEngine();
        engine.addDialect(new Java8TimeDialect());
        engine.setTemplateResolver(templateResolver());
//        engine.setTemplateEngineMessageSource(messageSource());
        engine.setEnableSpringELCompiler(true);
        return engine;
    }

    @Bean
    @Description("Thymeleaf View Resolver")
    public ViewResolver viewResolver(){
        var viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine());
        viewResolver.setOrder(1);
        return viewResolver;
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry);
        registry.addResourceHandler("/images/**","/styles/**")
                .addResourceLocations("/images/","/styles/");
    }

    @Override
    public void configureDefaultServletHandling(final DefaultServletHandlerConfigurer configurer) {
       configurer.enable();
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/","/home");
    }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(localeChangeInterceptor()).addPathPatterns("/*");
//        registry.addInterceptor(themeChangeInterceptor());
//    }
//
//    @Bean
//    MessageSource messageSource(){
//        var messageResource = new ReloadableResourceBundleMessageSource();
//        messageResource.setBasename("classpath:i18/global");
//        messageResource.setDefaultEncoding(StandardCharsets.UTF_8.name());
//        messageResource.setUseCodeAsDefaultMessage(true);
//        messageResource.setFallbackToSystemLocale(true);
//
//        return messageResource;
//    }
//
//    @Bean
//    LocaleChangeInterceptor localeChangeInterceptor(){
//        var localeChangeInterceptor = new LocaleChangeInterceptor();
//        localeChangeInterceptor.setParamName("lang");
//        return localeChangeInterceptor;
//    }
//
//    @Bean
//    ThemeChangeInterceptor themeChangeInterceptor(){
//        var themeChangeInterceptor = new ThemeChangeInterceptor();
//        themeChangeInterceptor.setParamName("theme");
//        return themeChangeInterceptor;
//    }
//
//    @Bean
//    CookieLocaleResolver localeResolver(){
//        var cookieLocaleResolver = new CookieLocaleResolver();
//        cookieLocaleResolver.setDefaultLocale(Locale.ENGLISH);
//        cookieLocaleResolver.setCookieMaxAge(3600);
//        cookieLocaleResolver.setCookieName("locale");
//        return cookieLocaleResolver;
//    }
//
//    @Bean
//    CookieThemeResolver themeResolver(){
//        var cookieThemeResolver = new CookieThemeResolver();
//        cookieThemeResolver.setDefaultThemeName("green");
//        cookieThemeResolver.setCookieMaxAge(3600);
//        cookieThemeResolver.setCookieName("theme");
//        return cookieThemeResolver;
//    }
}
