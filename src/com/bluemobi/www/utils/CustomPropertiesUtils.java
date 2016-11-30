package com.bluemobi.www.utils;

import java.text.MessageFormat;
import java.util.Properties;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Repository;

@Repository
public class CustomPropertiesUtils implements ApplicationListener<ApplicationEvent>{

private static final String PROP_PATH = "properties/config.properties";
    
    private static Properties prop;
    
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        try{
            Resource resource = new ClassPathResource(PROP_PATH);
            prop = PropertiesLoaderUtils.loadProperties(resource);
        }catch(Exception e){
            e.printStackTrace();
        }        
    }
    
    public static String getValue(String key){
        return prop.getProperty(key);
    }
    
    public static String getValue(String key,Object... args){
        String value = prop.getProperty(key);
        return MessageFormat.format(value, args);
    }
}
