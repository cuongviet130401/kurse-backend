package edu.uit.kurse.kursebackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class KurseBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(KurseBackendApplication.class, args);
    }

}
