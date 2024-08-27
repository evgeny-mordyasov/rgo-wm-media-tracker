package rgo.wm.media.tracker.test.model.generator;

import java.time.Year;
import java.util.concurrent.ThreadLocalRandom;

public final class Years {

    private Years() {
    }

    public static short randomMediaYear() {
        return (short) ThreadLocalRandom.current()
                .nextInt(
                        1895,
                        Year.now().getValue() + 1);
    }

    public static short randomMediaYearLessThan1895() {
        return (short) ThreadLocalRandom.current().nextInt(1, 1895);
    }
}
