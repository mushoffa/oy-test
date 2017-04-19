package com.oy.test.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by mushoffa on 19/04/17.
 */

@Parcel
public class Merchant {

    @SerializedName("address")
    private String address;

    @SerializedName("brand")
    private String brand;

    @SerializedName("brand_id")
    private String brandId;

    @SerializedName("description")
    private String description;

    @SerializedName("facilities")
    private List<String> facilities;

    @SerializedName("maps")
    private String geoLocation;

    @SerializedName("merchant_id")
    private String merchantId;

    @SerializedName("merchant_name")
    private String merchantName;

    @SerializedName("phone")
    private String phone;

    @SerializedName("username")
    private String username;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getFacilities() {
        return facilities;
    }

    public void setFacilities(List<String> facilities) {
        this.facilities = facilities;
    }

    public LatLng getGeoLocation() {
        String[] location = geoLocation.split(", ");

        return new LatLng(Double.parseDouble(location[0]), Double.parseDouble(location[1]));
    }

    public void setGeoLocation(String geoLocation) {
        this.geoLocation = geoLocation;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
