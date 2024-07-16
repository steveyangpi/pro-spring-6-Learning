package com.apress.prospring6.four.intf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class FileManager implements DisposableBean{
    private static Logger LOGGER = LoggerFactory.getLogger(FileManager.class);
    private Path file;

    public FileManager() {
        LOGGER.info("Creating bean of type {}",FileManager.class);
        try {
            file = Files.createFile(Path.of("sample"));
        }catch (IOException e){
            LOGGER.error("Could not create file");
        }
    }

    @Override
    public void destroy() throws Exception {
        LOGGER.info("Calling destroy() on bean of type {}",FileManager.class);
        if(file !=null){
            Files.deleteIfExists(file);
        }
    }
}

@Configuration
class DemoConfig{

    @Bean
    public FileManager fileManager(){
        return new FileManager();
    }
}
public class DisposableBeanDemo {
    public static void main(String[] args) {
        var ctx = new AnnotationConfigApplicationContext(DemoConfig.class);
        ctx.close();
    }
}
