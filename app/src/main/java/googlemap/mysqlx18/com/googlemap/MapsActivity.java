package googlemap.mysqlx18.com.googlemap;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    double longitude;
    double latitude;
    double speed;
    double altitude;
    Context context;
    private LocationManager locationManager;
    private LocationListener listener;
    public List<Address> addresses = new ArrayList<>();
    private String zipCode = "";
    boolean fineLocationAccepted;
    private static final int REQUEST_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getZipCode();
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        goMyLocation();
    }

    @Override
    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults) {

        Log.d("Zipcode", "onRequestPermissionsResult, permsRequestCode: " + permsRequestCode + " length of granResults: " + grantResults.length);
        try {
            switch (permsRequestCode) {
                case 200:
                    fineLocationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    for(int i=0; i<grantResults.length; i++){
                        Log.d("Zipcode", permissions[i] + " " + grantResults[i]);
                    }
            }
        } catch (Exception e) {

        }

        if(fineLocationAccepted == true){
            Log.d("Zipcode", "All permissions granted");
            getZipCode();
            goMyLocation();
        }

    }

    public void getZipCode(){
        // first check for permissions
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d("Zipcode", "checkSelfPermission");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Log.d("Zipcode", "Build version");
                requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.INTERNET}
                        , REQUEST_CODE);
            }
            Log.d("Zipcode", "No permission");
            //goMyLocation();
            return;
        }

        this.context = context;
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        final Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                longitude = location.getLongitude();
                latitude = location.getLatitude();
                speed = location.getSpeed();
                altitude = location.getAltitude();
                goMyLocation();

                Log.d("Zipcode", "Long: " + longitude);
                Log.d("Zipcode", "Lat: " + latitude);
                Log.d("Zipcode", "speed: " + speed);
                Log.d("Zipcode", "alt: " + altitude);

                try {
                    addresses = geocoder.getFromLocation(latitude, longitude, 1);
                    zipCode = addresses.get(0).getPostalCode().toString();
                    Log.d("Zipcode", zipCode);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
                //goMyLocation();
                Log.d("Zipcode", "OnStatusChanged");
            }

            @Override
            public void onProviderEnabled(String s) {
                Log.d("Zipcode", "onProviderEnabled");
            }

            @Override
            public void onProviderDisabled(String s) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };


        locationManager.requestLocationUpdates("gps", 60000, 0, listener);
        //goMyLocation();
    }

    public void goMyLocation(){
        LatLng myLocation = new LatLng(latitude, longitude);
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(myLocation).title("My Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));
    }
}
