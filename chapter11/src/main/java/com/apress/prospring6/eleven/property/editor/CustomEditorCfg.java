package com.apress.prospring6.eleven.property.editor;

import org.springframework.beans.factory.config.CustomEditorConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.Map;

@Configuration
public class CustomEditorCfg {

    @Bean
    public CustomEditorConfigurer customEditorConfigurer(){
        var cus = new CustomEditorConfigurer();
        cus.setCustomEditors(Map.of(LocalDate.class,LocalDatePropertyEditor.class));
        return cus;
    }
}
