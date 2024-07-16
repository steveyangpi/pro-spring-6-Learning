package com.apress.prospring6.four.jsr250;

import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class FileManager{
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

    @PreDestroy
    private void preDestroy() throws IOException{
        LOGGER.info("Calling preDestroy() on bean of type {}",FileManager.class);
        if(file !=null){
            Files.deleteIfExists(file);
        }
    }
}

@Configuration
class DemoConfig{

    @Bean
    FileManager fileManager(){
        return new FileManager();
    }
}
public class PreDestroyDemo {
    public static void main(String[] args) {
        var ctx = new AnnotationConfigApplicationContext(DemoConfig.class);
        ctx.close();
    }
}
