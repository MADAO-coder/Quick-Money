package com.example.a3130_group_6;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Class to extract the exact address using Latitude and Longitude
 *
 * @author  Pulkit Garg, Han Yang
 */
public class UserLocation {
    Geocoder geocoder;
    List<Address> addresses;
    Double latitude;
    Double longitude;
    String  radius;
    Activity activity;
    LatLng object;
    String address;

    public UserLocation(LatLng obj, Activity activity) {
        this.object = obj;
        this.latitude = obj.latitude;
        this.longitude = obj.longitude;
        this.activity = activity;
    }

    public UserLocation(Double latitude, Double longitude, String radius){
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
    }

    public UserLocation(){ }

    /**
     * Function: Method to create exact address using latitude and longitude
     * Parameters:
     * Returns: void
     *
     */
    protected void createAddress() throws IOException {
        geocoder = new Geocoder(activity,Locale.getDefault());
        addresses = geocoder.getFromLocation(latitude, longitude, 1);
        address = addresses.get(0).getAddressLine(0);
    }

    public void setRadius(String radius){
        this.radius = radius;
    }

    protected void setObject(LatLng obj){
        this.object = obj;
    }

    protected void setActivity(Activity activity){
        this.activity = activity;
    }

    protected String getAddress() {
        return address;
    }

    public Geocoder getGeocoder() {
        return geocoder;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getRadius() {
        return radius;
    }

    public Activity getActivity() {
        return activity;
    }

    public LatLng getObject() {
        return object;
    }
}
