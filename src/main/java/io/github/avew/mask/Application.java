package io.github.avew.mask;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.debug("This is a debug message with password=123456, username=av");
        log.info("This is a info message with password=123456, username=av");
        log.warn("This is a warn message with password=123456, username=av");
        log.error("This is a error message with password=123456, username=av");
    }
}
