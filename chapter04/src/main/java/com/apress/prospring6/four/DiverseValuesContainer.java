package com.apress.prospring6.four;


import groovy.swing.model.ValueHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
class ValuesHolder {
    List<String> stringList;
    InputStream inputStream;

    public ValuesHolder() {
        this.stringList = List.of("Mayer", "Psihoza", "Mazikeen");
        try {
            this.inputStream = new FileInputStream(
                    System.getProperty("java.io.tmpdir")
                            + System.getProperty("file.separator")
                            + "test.txt"
            );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<String> getStringList() {
        return stringList;
    }

    public InputStream getInputStream() {
        return inputStream;
    }
}

@Component("builtInSample")
public class DiverseValuesContainer {
    private byte[] bytes;
    private Character character;
    private Class<?> cls;
    private Boolean trueOrFalse;
    private List<String> stringList;
    private Date date;
    private Float floatValue;
    private File file;
    private InputStream stream;
    private Locale locale;
    private Pattern pattern;
    private Properties properties;
    private String trimString;
    private URL url;

    private static Logger LOGGER = LoggerFactory.getLogger(DiverseValuesContainer.class);

    @Value("A")
    public void setCharacter(Character character) {
        LOGGER.info("Setting character: {}", character);
        this.character = character;
    }

    @Value("java.lang.String")
    public void setCls(Class<?> cls) {
        LOGGER.info("Setting class: {}", cls.getName());
        this.cls = cls;
    }

    @Value("#{systemProperties['java.io.tmpdir']}#{systemProperties['file.separator']}test.txt")
    public void setFile(File file) {
        LOGGER.info("Setting file: {}", file.getAbsolutePath());
        this.file = file;
    }

    @Value("en_US")
    public void setLocale(Locale locale) {
        LOGGER.info("Setting locale: {}", locale.getDisplayName());
        this.locale = locale;
    }

    @Value("name=Ben age=41")
    public void setProperties(Properties properties) {
        LOGGER.info("Loaded {}", properties.size() + " properties");
        this.properties = properties;
    }

    @Value("https://iuliana-cosmina.com")
    public void setUrl(URL url) {
        LOGGER.info("Setting URL: {}", url.toExternalForm());
        this.url = url;
    }

    @Value("John Mayer")
    public void setBytes(byte... bytes) {
        LOGGER.info("Setting bytes: {}", Arrays.toString(bytes));
        this.bytes = bytes;
    }

    @Value("true")
    public void setTrueOrFalse(Boolean trueOrFalse) {
        LOGGER.info("Setting trueOrFalse: {}", trueOrFalse);
        this.trueOrFalse = trueOrFalse;
    }

    @Value("#{valuesHolder.stringList}")
    public void setStringList(List<String> stringList) {
        LOGGER.info("Setting stringList: {}", stringList);
        this.stringList = stringList;
    }

    @Value("20/08/1981")
    public void setDate(Date date) {
        LOGGER.info("Setting date: {}", date);
        this.date = date;
    }

    @Value("123.45678")
    public void setFloatValue(Float floatValue) {
        LOGGER.info("Setting float value: {}", floatValue);
        this.floatValue = floatValue;
    }

    @Value("#{valuesHolder.inputStream}")
    public void setStream(InputStream stream) {
        this.stream = stream;
        LOGGER.info("Setting stream & reading from it: {}",
                new BufferedReader(new InputStreamReader(stream)).lines().parallel().collect(Collectors.joining("\n")));
    }

    @Value("a*b")
    public void setPattern(Pattern pattern) {
        LOGGER.info("Setting pattern: {}", pattern);
        this.pattern = pattern;
    }

    @Value("  String need trimming  ")
    public void setTrimString(String trimString) {
        LOGGER.info("Setting trim string: {}", trimString);
        this.trimString = trimString;
    }

    public static void main(String[] args) throws Exception {
        File baseDir = new File(System.getProperty("java.io.tmpdir"));
        Path path = Files.createFile(Path.of(baseDir.getAbsolutePath(), "test.txt"));
        Files.writeString(path, "Hello World!");
        path.toFile().deleteOnExit();

        var ctx = new AnnotationConfigApplicationContext();
        ctx.register(ValuesHolder.class, DiverseValuesContainer.class);
        ctx.refresh();

        ctx.close();
    }
}
