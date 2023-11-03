package com.smallworld.util;

import com.smallworld.dal.repository.TransactionRepositoryImpl;
import com.smallworld.exceptions.InvalidPropertyException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

    private static final String APP_PROPERIES_FILE = "application.properties";

    public static String getProperty(String key) {
        try (InputStream input = TransactionRepositoryImpl.class.getClassLoader().getResourceAsStream(APP_PROPERIES_FILE)) {

            Properties prop = new Properties();

            if (input == null) {
                throw new InvalidPropertyException(String.format("Unable to find file %s", APP_PROPERIES_FILE), null);
            }

            //load a properties file from class path, inside static method
            prop.load(input);

            //get the property value and print it out
            return prop.getProperty(key);
        } catch (IOException ex) {
            throw new InvalidPropertyException(String.format("Unable to read file %s, message: %s", APP_PROPERIES_FILE, ex.getMessage()), ex);
        }
    }

    public static void setProperty(String key, String value) {
        try (InputStream input = TransactionRepositoryImpl.class.getClassLoader().getResourceAsStream(APP_PROPERIES_FILE)) {

            Properties prop = new Properties();

            if (input == null) {
                throw new InvalidPropertyException(String.format("Unable to find file %s", APP_PROPERIES_FILE), null);
            }

            //load a properties file from class path, inside static method
            prop.load(input);

            //get the property value and print it out
            prop.getProperty(key, value);
        } catch (IOException ex) {
            throw new InvalidPropertyException(String.format("Unable to read file %s, message: %s", APP_PROPERIES_FILE, ex.getMessage()), ex);
        }
    }
}
