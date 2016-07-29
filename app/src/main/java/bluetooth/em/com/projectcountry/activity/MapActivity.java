package bluetooth.em.com.projectcountry.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.dd.processbutton.iml.ActionProcessButton;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import UnlitechDevFramework.src.ud.framework.data.Response;
import bluetooth.em.com.projectcountry.R;
import bluetooth.em.com.projectcountry.controller.LocationController;
import bluetooth.em.com.projectcountry.data.Constants;
import bluetooth.em.com.projectcountry.model.AppGeneralModel;
import bluetooth.em.com.projectcountry.view.LocationView;
import bluetooth.em.com.projectcountry.view.MenuHolder;

/**
 * Created by Em on 6/7/2016.
 */
public class MapActivity extends AppCompatActivity implements LocationView, AppGeneralModel.AppGeneralModelObserver {
    AppGeneralModel mModel;
    LocationController mController;

    TextView tvlong,tvlat,tvadd;
    ActionProcessButton btnUpdateLoc;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        mModel = new AppGeneralModel();
        mModel.registerObserver(this);
        mController = new LocationController(this,mModel);
         toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
         tvlong = (TextView) findViewById(R.id.tv_long);
         tvlat = (TextView) findViewById(R.id.tv_lat);
         tvadd = (TextView) findViewById(R.id.tv_address);
        btnUpdateLoc = (ActionProcessButton) findViewById(R.id.btn_submit);
        btnUpdateLoc.setMode(ActionProcessButton.Mode.ENDLESS);
        btnUpdateLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mController.updateLocation();
            }
        });
        if(Constants.LOCATION_STATUS ==1) {
            Geocoder gcd = new Geocoder(this, Locale.getDefault());
            try {
                List<Address> add = gcd.getFromLocation(Constants.LATITUDE, Constants.LONGITUDE, 1);
                if (add.size() > 0) {
                    System.out.println("ADDRESS" + add.get(0).getLocality());
                    tvlong.setText("LONGITUDE: " + Constants.LONGITUDE);
                    tvlat.setText("LATITUDE: " + Constants.LATITUDE);
                    tvadd.setText(add.get(0).getAddressLine(0) + "," + add.get(0).getLocality() + "," +
                            add.get(0).getCountryName());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            findViewById(R.id.view).setVisibility(View.VISIBLE);
            tvlat.setVisibility(View.GONE);
            tvlong.setVisibility(View.GONE);
            tvadd.setText("You wont be able receive remittance transactions until you set your location. ");

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void secondResponseReceived(Response response, int type) {

    }

    @Override
    public void errorOnRequest(Exception exception) {

    }

    @Override
    public void responseReceived(Response response, int type) {
        mController.processResponse(response,type);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void showError(String title, String message, DialogInterface.OnDismissListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.AppCompatAlertDialogStyle);
        builder.setMessage(message);
        builder.setPositiveButton("OK", null);
        builder.show();
    }

    @Override
    public void showRetryError(String title, String message, DialogInterface.OnDismissListener listener) {

    }

    @Override
    public void showMap(int status, String longhitude, String latitude, String update) {
        findViewById(R.id.view).setVisibility(View.GONE);
        tvlat.setVisibility(View.VISIBLE);
        tvlong.setVisibility(View.VISIBLE);
        Geocoder gcd = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> add = gcd.getFromLocation(Constants.LATITUDE, Constants.LONGITUDE, 1);
            if (add.size() > 0) {
                System.out.println("ADDRESS" + add.get(0).getLocality());
                tvlong.setText("LONGITUDE: " + Constants.LONGITUDE);
                tvlat.setText("LATITUDE: " + Constants.LATITUDE);
                tvadd.setText(add.get(0).getAddressLine(0) + "," + add.get(0).getLocality() + "," +
                        add.get(0).getCountryName());

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public MenuHolder getCredentials() {
        MenuHolder holder = new MenuHolder();
        holder.btnUpdateLoc = (ActionProcessButton) findViewById(R.id.btn_submit);
        return holder;
    }
}
