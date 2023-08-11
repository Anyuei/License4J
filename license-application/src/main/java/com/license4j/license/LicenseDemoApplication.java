package com.license4j.license;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LicenseDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(LicenseDemoApplication.class, args);
    }

}
