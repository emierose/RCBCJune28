package bluetooth.em.com.projectcountry.activity;

/**
 * Created by Em on 6/7/2016.
 */

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import bluetooth.em.com.projectcountry.R;

public class GPSTrackingActivity extends Activity {

    private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 1; // in Meters
    private static final long MINIMUM_TIME_BETWEEN_UPDATES = 1000; // in Milliseconds

    protected LocationManager locationManager;

    protected Button retrieveLocationButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.track);

        retrieveLocationButton = (Button) findViewById(R.id.btnShowLocation);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                MINIMUM_TIME_BETWEEN_UPDATES,
                MINIMUM_DISTANCE_CHANGE_FOR_UPDATES,
                new MyLocationListener()
        );

        retrieveLocationButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showCurrentLocation();
            }
        });

    }

    protected void showCurrentLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    System.out.println("LOC:"+location);
        if (location != null) {
            String message = String.format(
                    "Current Location \n Longitude: %1$s \n Latitude: %2$s",
                    location.getLongitude(), location.getLatitude()
            );
            Toast.makeText(GPSTrackingActivity.this, message,
                    Toast.LENGTH_LONG).show();
        }
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            Location l = locationManager.getLastKnownLocation(provider);
            System.out.println("last known location, provider: %s, location: %s"+provider+" "+
                    l);

            if (l == null) {
                continue;
            }else{

            }
            if (bestLocation == null
                    || l.getAccuracy() < bestLocation.getAccuracy()) {
                System.out.println("found best last known location: %s"+ l);
                bestLocation = l;
            }
        }

    }

    private class MyLocationListener implements LocationListener {

        public void onLocationChanged(Location location) {
            String message = String.format(
                    "New Location \n Longitude: %1$s \n Latitude: %2$s",
                    location.getLongitude(), location.getLatitude()
            );
            Toast.makeText(GPSTrackingActivity.this, message, Toast.LENGTH_LONG).show();
        }

        public void onStatusChanged(String s, int i, Bundle b) {
            Toast.makeText(GPSTrackingActivity.this, "Provider status changed",
                    Toast.LENGTH_LONG).show();
        }

        public void onProviderDisabled(String s) {
            Toast.makeText(GPSTrackingActivity.this,
                    "Provider disabled by the user. GPS turned off",
                    Toast.LENGTH_LONG).show();
        }

        public void onProviderEnabled(String s) {
            Toast.makeText(GPSTrackingActivity.this,
                    "Provider enabled by the user. GPS turned on",
                    Toast.LENGTH_LONG).show();
        }

    }

}