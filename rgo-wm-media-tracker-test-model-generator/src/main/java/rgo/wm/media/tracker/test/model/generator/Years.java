package rgo.wm.media.tracker.test.model.generator;

import java.time.Year;

import static rgo.wm.common.test.utils.random.ShortRandom.randomShortInclusive;

public final class Years {

    private Years() {
    }

    public static short randomMediaYear() {
        return randomShortInclusive(1895, Year.now().getValue());
    }

    public static short randomMediaYearLessThan1895() {
        return randomShortInclusive(1, 1895);
    }
}
