package com.mudongheng.app.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author 幕冬
 * @since 2022年04月18日
 */
@Slf4j
@Configuration
public class PasswordEncoderConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        log.info("启用Bcrypt密码处理器");
        return new BCryptPasswordEncoder();
    }

}
