package ru.realityfamily.userservice.conf;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.Properties;

public class TrimPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer { // for trim() any trailing whitespaces in @Value properties automatically

    @Override
    protected String resolvePlaceholder( String placeholder, Properties props ) {
        String value = super.resolvePlaceholder( placeholder, props );

        return (value != null ? value.trim() : null );
    }
}
