package com.project.readers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@SpringBootApplication
public class ReadersApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReadersApplication.class, args);
    }

}
