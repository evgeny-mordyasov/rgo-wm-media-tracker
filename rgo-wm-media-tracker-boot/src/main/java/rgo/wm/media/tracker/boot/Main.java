package rgo.wm.media.tracker.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
