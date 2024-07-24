package com.apress.prospring6.eleven.property.editor;

import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.config.CustomEditorConfigurer;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@Configurable
public class CustomRegistrarCfg {

    @Bean
    public PropertyEditorRegistrar registrar(){
        return registry ->
                registry.registerCustomEditor(LocalDate.class,new LocalDatePropertyEditor());
    }

    @Bean
    public CustomEditorConfigurer customEditorConfigurer(){
        var cus = new CustomEditorConfigurer();
        var registrars = new PropertyEditorRegistrar[1];
        registrars[0] = registrar();
        cus.setPropertyEditorRegistrars(registrars);
        return cus;
    }
}
