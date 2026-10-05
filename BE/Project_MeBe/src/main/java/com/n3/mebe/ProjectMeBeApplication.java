package com.n3.mebe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ProjectMeBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectMeBeApplication.class, args);
    }

}
