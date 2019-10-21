package com.cmcc.v2x.dso.authcs;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.cmcc.v2x.dso.authcs.mapper")
public class AuthcsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthcsApplication.class, args);
    }

}
