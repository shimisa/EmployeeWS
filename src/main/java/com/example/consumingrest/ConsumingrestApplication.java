package com.example.consumingrest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Slf4j
@SpringBootApplication
public class ConsumingrestApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumingrestApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
        return args -> {
            Employee[] employees =
                    restTemplate.getForObject("http://localhost:8080/employees", Employee[].class);
            List<Employee> employeeList = Arrays.asList(employees);
            log.info(employeeList.toString());
        };
    }

}
