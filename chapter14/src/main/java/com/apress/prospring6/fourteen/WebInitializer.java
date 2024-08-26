package com.apress.prospring6.fourteen;

import jakarta.servlet.Filter;
import jakarta.servlet.MultipartConfigElement;
import jakarta.servlet.ServletRegistration;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import java.nio.charset.StandardCharsets;

public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{BasicDataSourceCfg.class, TransactionCfg.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected Filter[] getServletFilters() {
        final CharacterEncodingFilter cef = new CharacterEncodingFilter();
        cef.setEncoding(StandardCharsets.UTF_8.name());
        cef.setForceEncoding(true);
        return new Filter[]{new HiddenHttpMethodFilter(),cef};
    }

    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        registration.setInitParameter("throwExceptionIfNoHandlerFound", "true");
        registration.setMultipartConfig(getMultipartConfigElement());
        super.customizeRegistration(registration);
    }

    private MultipartConfigElement getMultipartConfigElement() {
        return new MultipartConfigElement(null,MAX_FILE_SIZE,MAX_REQUEST_SIZE,FILE_SIZE_THRESHOLD);
    }

    private static final long MAX_FILE_SIZE = 5000000;

    private static final long MAX_REQUEST_SIZE = 5000000;

    private static final int FILE_SIZE_THRESHOLD = 0;

}
