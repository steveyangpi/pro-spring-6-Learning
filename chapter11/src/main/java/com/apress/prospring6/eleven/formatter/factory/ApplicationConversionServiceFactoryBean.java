package com.apress.prospring6.eleven.formatter.factory;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Service("conversionService")
public class ApplicationConversionServiceFactoryBean extends FormattingConversionServiceFactoryBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationConversionServiceFactoryBean.class);

    private static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
    private DateTimeFormatter dateTimeFormatter;
    private String datePattern = DEFAULT_DATE_PATTERN;
    private final Set<Formatter<?>> formatters = new HashSet<>();

    public String getDatePattern(){return datePattern;}


    @Autowired(required = false)
    public void setDatePattern(String datePattern){this.datePattern = datePattern;}

    @PostConstruct
    public void init(){
        dateTimeFormatter = DateTimeFormatter.ofPattern(datePattern);
        formatters.add(getDateTimeFormatter());
    }

    public Formatter<LocalDate> getDateTimeFormatter(){
        return new Formatter<>(){
            @Override
            public LocalDate parse(String source, Locale locale) throws ParseException {
                LOGGER.info("Parsing date string: " + source);
                return LocalDate.parse(source,dateTimeFormatter);
            }

            @Override
            public String print(LocalDate source, Locale locale) {
               LOGGER.info("Formatting datetime: "+ source);
               return source.format(dateTimeFormatter);
            }
        };
    }
}
