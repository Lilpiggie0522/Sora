package com.piggie;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * ClassName: SoraApplication
 * Package: com.piggie
 * Description:
 *
 * @Author Piggie
 * @Create 5/02/2024 5:02 pm
 * @Version 1.0
 */
@SpringBootApplication
@Slf4j
@EnableTransactionManagement
@EnableCaching
public class SoraApplication {
    public static void main(String[] args) {
        SpringApplication.run(SoraApplication.class, args);
        log.info("server started");
    }
}
