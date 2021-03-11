package com.example.a3130_group_6;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.io.Serializable;
import java.util.ArrayList;

public class AddListingMap extends AppCompatActivity implements OnMapReadyCallback {

    public static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 0;
    public static final String LOCATION_PERMISSION = android.Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String LOCATION_PREF = "locationPref";
    CheckExistingUserName user;

    Context context;
    Activity activity;
    Spinner spinner;
    String radius_array[] = {"5", "10", "15", "20", "25"};
    int radius = 5;

    private GoogleMap googleMap;
    private Serializable escolas;
    private ProgressDialog dialog;
    private Circle mCircle;
    private Marker mMarker;

    LocationManager manager;
    LatLng currentLocation;

    ArrayList<Places> placesList = new ArrayList<>();
    PlacesAdapter adapter;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_map);
        Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);


        placesList.add(new Places("Dalhousie University", new LatLng(44.6354974, -63.5883309), 0));
        placesList.add(new Places("Halifax Airport", new LatLng(44.8751631 ,-63.5051646), 0));
        placesList.add(new Places("Middle Sackville", new LatLng(44.7906801, -63.7247304), 0));
        placesList.add(new Places("Clayton Park", new LatLng(44.6541640, -63.6503307), 0));


        context = AddListingMap.this;
        activity = AddListingMap.this;

        // spinner created to create a dropdown menu with the radius array
        spinner = findViewById(R.id.spinner);
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, radius_array));

        user = new CheckExistingUserName();
        checkPermissions();

        manager = (LocationManager) getSystemService(LOCATION_SERVICE);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            return;
        }
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000,
                5, listener);


        mapFragment.getMapAsync(this);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                radius = Integer.parseInt(radius_array[position]);
                Log.d("Radius", "" + radius);
                if (currentLocation != null) {
                    googleMap.clear();
                    drawMarkerWithCircle(currentLocation);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        list = findViewById(R.id.listView);
        adapter = new PlacesAdapter();
        list.setAdapter(adapter);
    }

    protected void checkPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            checkLocationPermission(activity, context, LOCATION_PERMISSION, LOCATION_PREF);
        } else {
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    private void checkLocationPermission(final Activity activity, final Context context, final String Permission, final String prefName) {

        PermissionUtil.checkPermission(activity, context, Permission, prefName,
                new PermissionUtil.PermissionAskListener() {
                    @Override
                    public void onPermissionAsk() {
                        ActivityCompat.requestPermissions(activity,
                                new String[]{Permission},
                                MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                    }

                    @Override
                    public void onPermissionPreviouslyDenied() {
                        //show a dialog explaining is permission denied previously , but app require it and then request permission

                        showToast("Permission previously Denied.");

                        ActivityCompat.requestPermissions(activity,
                                new String[]{Permission},
                                MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                    }

                    @Override
                    public void onPermissionDisabled() {
                        // permission check box checked and permission denied previously .
                        askUserToAllowPermissionFromSetting();
                    }

                    @Override
                    public void onPermissionGranted() {
                        showToast("Permission Granted.");
                    }
                });
    }

    private void askUserToAllowPermissionFromSetting() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set title
        alertDialogBuilder.setTitle("Permission Required:");

        // set dialog message
        alertDialogBuilder
                .setMessage("Kindly allow Permission from App Setting, without this permission app could not show maps.")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, close
                        // current activity
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                        showToast("Permission forever Disabled.");
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the task you need to do.
                    // Obtain the SupportMapFragment and get notified when the map is ready to be used.
                } else {
                     showToast("Permission denied,without permission can't access maps.");
                    // permission denied, boo! Disable the functionality that depends on this permission.
                }
                return;
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;

        // Showing / hiding your current location
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            return;
        }
        googleMap.setMyLocationEnabled(true);

        // Enable / Disable zooming controls
        googleMap.getUiSettings().setZoomControlsEnabled(true);

        // Enable / Disable my location button
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);

        // Enable / Disable Compass icon
        googleMap.getUiSettings().setCompassEnabled(true);

        // Enable / Disable Rotate gesture
        googleMap.getUiSettings().setRotateGesturesEnabled(true);

        // Enable / Disable zooming functionality
        googleMap.getUiSettings().setZoomGesturesEnabled(true);

        if (currentLocation != null) {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 10));
            drawMarkerWithCircle(currentLocation);
        }
    }

    private void drawMarkerWithCircle(LatLng position) {
        double radiusInMeters = radius * 1000.0;  // increase decrease this distancce as per your requirements
        int strokeColor = 0xffff0000; //red outline
        int shadeColor = 0x44ff0000; //opaque red fill

        CircleOptions circleOptions = new CircleOptions()
                .center(position)
                .radius(radiusInMeters)
                .fillColor(shadeColor)
                .strokeColor(strokeColor)
                .strokeWidth(8);
        mCircle = googleMap.addCircle(circleOptions);

    }

    LocationListener listener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

            currentLocation = new LatLng(location.getLatitude(), location.getLongitude());

            Log.d("Location", "" + location.getLatitude() + "," + location.getLongitude());

            googleMap.clear();
            drawMarkerWithCircle(currentLocation);

            for (int i = 0; i < placesList.size(); i++) {
                float[] distance = new float[2];
                Location.distanceBetween(currentLocation.latitude, currentLocation.longitude,
                        placesList.get(i).place_latlng.latitude, placesList.get(i).place_latlng.longitude, distance);

                float radius = Float.parseFloat(mCircle.getRadius() + "");
                float distanceInMeter = Float.parseFloat(distance[0] + "");
                if (distanceInMeter > radius) {
                    placesList.get(i).status = 0;
//                    Toast.makeText(getBaseContext(), "You are Outside of the circle, Distance from center: " + distance[0] + " Radius: " + mCircle.getRadius(), Toast.LENGTH_LONG).show();
                } else {
                    placesList.get(i).status = 1;
//                    Toast.makeText(getBaseContext(), "Your are Inside the circle, Distance from center: " + distance[0] + " Radius: " + mCircle.getRadius(), Toast.LENGTH_LONG).show();
                }
            }

            adapter.notifyDataSetChanged();

            if (googleMap != null) {
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 10));
            }
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    class Places {
        String place_name;
        LatLng place_latlng;
        int status;

        public Places(String place_name, LatLng place_latlng, int status) {
            this.place_name = place_name;
            this.place_latlng = place_latlng;
            this.status = status;
        }
    }

    public class PlacesAdapter extends BaseAdapter {
        private Context context;

        PlacesAdapter() {
        }

        @Override
        public int getCount() {
            return placesList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View rowView = convertView;

            if (rowView == null) {
                LayoutInflater inflater = getLayoutInflater();
                rowView = inflater.inflate(R.layout.list_row, null);

                ViewHolder viewHolder = new ViewHolder();
                viewHolder.tvPlace = rowView
                        .findViewById(R.id.textPlaceName);
                viewHolder.imgStatus = rowView
                        .findViewById(R.id.imageStatus);
                rowView.setTag(viewHolder);
            }

            ViewHolder holder = (ViewHolder) rowView.getTag();
            holder.tvPlace.setText(placesList.get(position).place_name);
            if (placesList.get(position).status == 1)
                holder.imgStatus.setBackgroundResource(R.drawable.circle_green);
            else
                holder.imgStatus.setBackgroundResource(R.drawable.circle_red);

            return rowView;
        }

        class ViewHolder {
            TextView tvPlace;
            ImageView imgStatus;
        }
    }
}
