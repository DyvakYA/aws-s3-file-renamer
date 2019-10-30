package com.dyvak.configuration;

import com.dyvak.exception.WrongDateFormatException;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@ConfigurationPropertiesBinding
public class ApplicationPropertiesDateConverter implements Converter<String, Date> {

    @Override
    public Date convert(String source) {
        if (source == null) {
            return null;
        }
        try {
            return new SimpleDateFormat("dd-MM-yyyy").parse(source);
        } catch (ParseException e) {
            throw new WrongDateFormatException();
        }
    }
}
