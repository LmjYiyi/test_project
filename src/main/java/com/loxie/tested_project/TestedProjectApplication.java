package com.loxie.tested_project;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.loxie.tested_project.repository") // 扫描 MyBatis Mapper 接口
public class TestedProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestedProjectApplication.class, args);
    }

}
