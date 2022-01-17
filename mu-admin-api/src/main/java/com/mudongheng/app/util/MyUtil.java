package com.mudongheng.app.util;

import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * @author 幕冬
 * @since 2022年01月11日
 */
@Slf4j
public class MyUtil {

    public static byte[] getThumbnailImage(File file) {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            return fileInputStream.readAllBytes();
        } catch (IOException e) {
            log.error(e.getMessage());
            return null;
        }
    }

}
