package utilities;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigProp {
    public static Properties configfile;

    static {
        try {
            String path = Constants.config_filePath;
            FileInputStream input = new FileInputStream(path);
            configfile = new Properties();
            configfile.load(input);
            input.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String getProperty(String keyName) {
        return configfile.getProperty(keyName);
    }
}
