package org.homework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"org.homework", "org.example"})
public class BishopPrototypeApplication {
    public static void main(String[] args) {
        SpringApplication.run(BishopPrototypeApplication.class, args);
    }
}