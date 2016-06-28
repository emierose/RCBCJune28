package bluetooth.em.com.projectcountry.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import bluetooth.em.com.projectcountry.R;
import bluetooth.em.com.projectcountry.controller.LocationController;
import bluetooth.em.com.projectcountry.data.Constants;
import bluetooth.em.com.projectcountry.model.AppGeneralModel;

/**
 * Created by Em on 6/6/2016.
 */
public class MapFragment extends SupportMapFragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnMapLongClickListener,
        GoogleMap.OnMapClickListener,
        GoogleMap.OnMarkerClickListener{
    private GoogleApiClient mGoogleApiClient;
    private Location mCurrentLocation;
    protected LocationManager locationManager;
    private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 10; // in Meters
    private static final long MINIMUM_TIME_BETWEEN_UPDATES = 5000; // in Milliseconds
    AppGeneralModel mModel;
    LocationController mController;
    int USER_LOC_STATUS;
    String LOC_LONG, LOC_LAT,UPDATE;
    View view;
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view= view;
        setHasOptionsMenu(true);

//        mModel = new AppGeneralModel();
//        mModel.registerObserver(this);
//        mController = new LocationController(this,mModel);
//        mController.requestLastLocation();

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        initListeners();
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                MINIMUM_TIME_BETWEEN_UPDATES,
                MINIMUM_DISTANCE_CHANGE_FOR_UPDATES,
                new MyLocationListener()
        );
    }
    protected void showCurrentLocation() {
if(Constants.LOCATION_STATUS ==1){

    MarkerOptions options = new MarkerOptions().position(new LatLng(Constants.LATITUDE, Constants.LONGITUDE));
    options.title("MY LOCATION");
    options.icon(BitmapDescriptorFactory.fromBitmap(
            BitmapFactory.decodeResource(getResources(), R.mipmap.ic_marker)));
    getMap().addMarker(options);
    CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(new LatLng(Constants.LATITUDE, Constants.LONGITUDE), 12);
    getMap().animateCamera(yourLocation);
}else {
    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
    System.out.println("LOC:" + location);
    if (location != null) {
        String message = String.format(
                "Current Location \n Longitude: %1$s \n Latitude: %2$s",
                location.getLongitude(), location.getLatitude()
        );
        Toast.makeText(getActivity(), message,
                Toast.LENGTH_LONG).show();
    }
    List<String> providers = locationManager.getProviders(true);
    Location bestLocation = null;
    for (String provider : providers) {
        Location l = locationManager.getLastKnownLocation(provider);
        System.out.println("last known location, provider: %s, location: %s" + provider + " " +
                l);
        if (l == null) {
            continue;
        }
        if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
            System.out.println("found best last known location: %s" + l);
            bestLocation = l;
            MarkerOptions options = new MarkerOptions().position(new LatLng(l.getLatitude(), l.getLongitude()));
            options.title("MY LOCATION");
            options.icon(BitmapDescriptorFactory.fromBitmap(
                    BitmapFactory.decodeResource(getResources(), R.mipmap.ic_marker)));
            getMap().addMarker(options);
            CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(new LatLng(l.getLatitude(), l.getLongitude()), 12);
            getMap().animateCamera(yourLocation);
            Constants.LATITUDE = l.getLatitude();
            Constants.LONGITUDE = l.getLongitude();
        }
    }
}

    }

    private class MyLocationListener implements LocationListener {

        public void onLocationChanged(Location location) {
//            String message = String.format(
//                    "New Location \n Longitude: %1$s \n Latitude: %2$s",
//                    location.getLongitude(), location.getLatitude()
//            );
//            Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();

        }

        public void onStatusChanged(String s, int i, Bundle b) {
//            Toast.makeText(getActivity(), "Provider status changed",
//                    Toast.LENGTH_LONG).show();
        }

        public void onProviderDisabled(String s) {
//            Toast.makeText(getActivity(),
//                    "Provider disabled by the user. GPS turned off",
//                    Toast.LENGTH_LONG).show();
            showSettingsAlert();
        }

        public void onProviderEnabled(String s) {
            Toast.makeText(getActivity(),
                    "Provider enabled by the user. GPS turned on",
                    Toast.LENGTH_LONG).show();
        }

    }
    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing the Settings button.
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                getActivity().startActivity(intent);
            }
        });

        // On pressing the cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    private void initListeners() {
        getMap().setOnMarkerClickListener(this);
        getMap().setOnMapLongClickListener(this);
        getMap().setOnInfoWindowClickListener(this);
        getMap().setOnMapClickListener(this);
    }

    private void removeListeners() {
        if (getMap() != null) {
            getMap().setOnMarkerClickListener(null);
            getMap().setOnMapLongClickListener(null);
            getMap().setOnInfoWindowClickListener(null);
            getMap().setOnMapClickListener(null);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        removeListeners();
    }

    private void initCamera(Location location) {
        getMap().setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        getMap().setTrafficEnabled(true);
        getMap().setMyLocationEnabled(true);
        getMap().getUiSettings().setZoomControlsEnabled(true);

        showCurrentLocation();
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        if( mGoogleApiClient != null && mGoogleApiClient.isConnected() ) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation( mGoogleApiClient );
        initCamera(mCurrentLocation);

    }

    @Override
    public void onConnectionSuspended(int i) {
        //handle play services disconnecting if location is being constantly used
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        //Create a default location if the Google API Client fails. Placing location at Googleplex
        mCurrentLocation = new Location( "" );
        mCurrentLocation.setLatitude( 37.422535 );
        mCurrentLocation.setLongitude( -122.084804 );
        initCamera(mCurrentLocation);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Toast.makeText(getActivity(), "Clicked on marker", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.showInfoWindow();
        return true;
    }

    @Override
    public void onMapClick(LatLng latLng) {

//        MarkerOptions options = new MarkerOptions().position( latLng );
//        options.title( getAddressFromLatLng( latLng ) );
//
//        options.icon( BitmapDescriptorFactory.defaultMarker() );
//        getMap().addMarker( options );
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
//        MarkerOptions options = new MarkerOptions().position( latLng );
//        options.title( getAddressFromLatLng(latLng) );
//
//        options.icon( BitmapDescriptorFactory.fromBitmap(
//                BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher) ) );
//
//        getMap().addMarker(options);
    }

    private String getAddressFromLatLng( LatLng latLng ) {
        Geocoder geocoder = new Geocoder( getActivity() );

        String address = "";
        try {
            address = geocoder.getFromLocation( latLng.latitude, latLng.longitude, 1 ).get( 0 ).getAddressLine( 0 );
        } catch (IOException e ) {
        }

        return address;
    }

    private void toggleTraffic() {
        getMap().setTrafficEnabled(!getMap().isTrafficEnabled());
    }


}
