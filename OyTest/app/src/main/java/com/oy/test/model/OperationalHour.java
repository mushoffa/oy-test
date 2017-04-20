package com.oy.test.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.Map;

/**
 * Created by mushoffa on 19/04/17.
 */

@Parcel
public class OperationalHour {

    @SerializedName("day_of_week")
    private String dayOfWeek;

    @SerializedName("timings")
    private Map<String, String> timings;

    private String closeTime;

    private String openTime;

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Map<String, String> getTimings() {
        return timings;
    }

    public void setTimings(Map<String, String> timings) {
        this.timings = timings;
        this.closeTime = timings.get("close_time");
        this.openTime = timings.get("open_time");
    }

    public String getCloseTime() {
        return closeTime;
    }

    public String getOpenTime() {
        return openTime;
    }
}
