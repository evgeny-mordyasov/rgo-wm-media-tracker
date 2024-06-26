package rgo.wm.media.tracker.rest.api.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public class MediaSaveRequest {

    @NotEmpty(message = "Name must not be null or empty.")
    private String name;

    @Min(value = 1895, message = "Year must be greater than 1894.")
    private int year;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
