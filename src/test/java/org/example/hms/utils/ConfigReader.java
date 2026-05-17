package org.example.hms.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static final Properties props = new Properties();

    static {
        try (InputStream in = ConfigReader.class
                .getClassLoader().getResourceAsStream("config.properties")) {
            if (in == null) throw new RuntimeException("config.properties not found in src/test/resources");
            props.load(in);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    public static String get(String key) {
        // system property (-Dkey=value) takes priority over config.properties
        String sysProp = System.getProperty(key);
        if (sysProp != null) return sysProp.trim();
        String value = props.getProperty(key);
        if (value == null) throw new RuntimeException("Missing property: " + key);
        return value.trim();
    }

    public static String getBaseUrl()       { return get("base.url"); }
    public static String getApiBaseUrl()    { return get("api.base.url"); }
    public static String getAdminUsername() { return get("admin.username"); }
    public static String getAdminPassword() { return get("admin.password"); }
    public static String getBrowser()       { return get("browser"); }
    public static boolean isHeadless()      { return Boolean.parseBoolean(get("headless")); }
    public static int getImplicitWait()     { return Integer.parseInt(get("implicit.wait")); }
    public static int getExplicitWait()     { return Integer.parseInt(get("explicit.wait")); }
}
