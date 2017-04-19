package com.oy.test.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by mushoffa on 19/04/17.
 */

@Parcel
public class OperationalHour {

    @SerializedName("day_of_week")
    private String dayOfWeek;

    @SerializedName("close_time")
    private String closeTime;

    @SerializedName("open_time")
    private String openTime;
}
