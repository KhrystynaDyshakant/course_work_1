package ua.lviv.iot.spring.first.project.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan({"ua.lviv.iot.spring.first.project"})
public class RestApplication {

    public static void main(final String[] args) {
        SpringApplication.run(RestApplication.class, args);
    }

}
