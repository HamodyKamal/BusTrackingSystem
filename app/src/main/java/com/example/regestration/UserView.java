package com.example.regestration;

import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class UserView extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Button SignOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        SignOut = (Button) findViewById(R.id.SignOut);
        SignOut.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        LatLng Jeddah = new LatLng(21.578784, 39.165353);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(Jeddah));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
        if(!getDriverAroundStarted)
            getDriversAround();
    }
    List<Marker> markerList = new ArrayList<Marker>();

    boolean getDriverAroundStarted = false;
    private void getDriversAround(){

        getDriverAroundStarted = true;

        DatabaseReference driversLocation =   FirebaseDatabase.getInstance().getReference().child("Driver");

        GeoFire geoFire = new GeoFire(driversLocation);

        GeoQuery geoQuery = geoFire.queryAtLocation(new GeoLocation(21.578784, 39.165353), 10000);

        geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(String key, GeoLocation location) {
                for (Marker markerIt : markerList){
                    if (markerIt.getTag().equals(key))
                        return;
                }

                LatLng driverLocation = new LatLng(location.latitude, location.longitude);
                Marker mDriverMarker = mMap.addMarker(new MarkerOptions().position(driverLocation));
                mDriverMarker.setTag(key);

                markerList.add(mDriverMarker);

            }

            @Override
            public void onKeyExited(String key) {

                for (Marker markerIt : markerList){
                    if (markerIt.getTag().equals(key))
                        markerIt.remove();
                    markerList.remove(markerIt);
                    return;

                }

            }

            @Override
            public void onKeyMoved(String key, GeoLocation location) {

                for (Marker markerIt : markerList){
                    if (markerIt.getTag().equals(key)){
                        markerIt.setPosition(new LatLng(location.latitude, location.longitude));
                    }

                }

            }

            @Override
            public void onGeoQueryReady() {

            }

            @Override
            public void onGeoQueryError(DatabaseError error) {

            }
        });
    }


}