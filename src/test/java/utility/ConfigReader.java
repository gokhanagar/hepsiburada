package utility;

import java.io.FileInputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConfigReader {
    private static Properties properties;
    private static final Logger logger = LogManager.getLogger(ConfigReader.class);

    static {
        loadProperties();
    }

    private static void loadProperties() {
        try {
            FileInputStream input = new FileInputStream("src/test/resources/config.properties");
            properties = new Properties();
            properties.load(input);
            input.close();
        } catch (Exception e) {
            logger.error("Config file could not be loaded: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }
} 