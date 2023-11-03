package com.project.readers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class) // DB 연결 후 (exclude~ ) 삭제하기
public class ReadersApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReadersApplication.class, args);
    }

}
