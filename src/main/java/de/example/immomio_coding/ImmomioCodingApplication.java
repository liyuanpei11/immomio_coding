package de.example.immomio_coding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class ImmomioCodingApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImmomioCodingApplication.class, args);
    }
}
