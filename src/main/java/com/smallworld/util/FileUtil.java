package com.smallworld.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smallworld.exceptions.InvalidFilePathException;
import com.smallworld.exceptions.JsonConversionException;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;

@Slf4j
public class FileUtil {
    private static final ObjectMapper typedMapper = new ObjectMapper();

    public static <T> T toObjectFromTypedJson(String json, Class<T> clazz) {
        T obj = null;
        try {
            obj = typedMapper.readValue(json, clazz);
        } catch (IOException e) {
            log.warn("Conversion of JSON to object failed: " + e.getMessage());
            throw new JsonConversionException(e.getMessage(), e);
        }
        return obj;
    }

    public static String readUsingFileInputStream(String fileName) {
        FileInputStream fis = null;
        byte[] buffer = new byte[10];
        StringBuilder sb = new StringBuilder();
        try {
            fis = new FileInputStream(fileName);

            while (fis.read(buffer) != -1) {
                sb.append(new String(buffer));
                buffer = new byte[10];
            }
            fis.close();

        } catch (IOException e) {
            log.warn(e.getMessage(), e);
            throw new InvalidFilePathException(String.format("filname:%s, message:%s", fileName, e.getMessage()), e);
        } finally {
            if (fis != null)
                try {
                    fis.close();
                } catch (IOException e) {
                    log.warn(e.getMessage(), e);
                    throw new InvalidFilePathException(String.format("filname:%s, message:%s", fileName, e.getMessage()), e);
                }
        }
        return sb.toString();
    }
}
