package com.example.a3130_group_6;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GetExactAddress {
    Geocoder geocoder;
    List<Address> addresses;
    Double latitude;
    Double longitude;
    Activity activity;
    String address, city, state, country, postalCode, knownName;

    public GetExactAddress(LatLng obj, Activity activity) {
        this.latitude = obj.latitude;
        this.longitude = obj.longitude;
        this.activity = activity;
    }

    protected void createAddress() throws IOException {
        geocoder = new Geocoder(activity,Locale.getDefault());
        address = addresses.get(0).getAddressLine(0);
    }

    protected String getAddress() {
        return address;
    }
}
