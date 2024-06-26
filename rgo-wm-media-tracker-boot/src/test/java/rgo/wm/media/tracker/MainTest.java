package rgo.wm.media.tracker;

import org.springframework.boot.SpringApplication;
import rgo.wm.media.tracker.tests.Containers;

public class MainTest {

    public static void main(String[] args) {
        SpringApplication.from(Main::main)
                .with(Containers.class)
                .run(args);
    }
}
