package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigUtil {
    private static Properties properties = new Properties();

    public static String getProperty(String propertyName) {
        try {
            properties.load(new FileInputStream("src\\main\\resources\\config\\config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties.getProperty(propertyName);
    }
}
