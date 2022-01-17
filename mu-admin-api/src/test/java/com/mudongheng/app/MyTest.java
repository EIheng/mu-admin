package com.mudongheng.app;

import org.junit.jupiter.api.Test;

import java.net.URL;

/**
 * @author 幕冬
 * @since 2021年10月26日
 */
public class MyTest {

    @Test
    void test1() {
        String s = "http://localhost:9908/api/gallery/get-image?path=\\hello";
        try {
            URL url = new URL(s);
            System.out.println(url.toURI());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
