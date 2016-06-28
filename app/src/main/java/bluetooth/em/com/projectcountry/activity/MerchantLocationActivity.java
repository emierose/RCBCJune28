package bluetooth.em.com.projectcountry.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import UnlitechDevFramework.src.ud.framework.data.Response;
import bluetooth.em.com.projectcountry.R;
import bluetooth.em.com.projectcountry.controller.MerchantLocationController;
import bluetooth.em.com.projectcountry.model.AppGeneralModel;
import bluetooth.em.com.projectcountry.view.LocationView;
import bluetooth.em.com.projectcountry.view.MenuHolder;

/**
 * Created by Em on 6/23/2016.
 */
public class MerchantLocationActivity extends AppCompatActivity implements LocationView, AppGeneralModel.AppGeneralModelObserver{
    MerchantLocationController mController;
    AppGeneralModel mModel;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_merchant_location);

//        mModel = new AppGeneralModel();
//        mModel.registerObserver(this);
//        mController = new MerchantLocationController(this, mModel);
//        mController.requestMerchantLocation();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }
    @Override
    public void secondResponseReceived(Response response, int type) {
        mController.processResponse(response, type);

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
    public void errorOnRequest(Exception exception) {

    }

    @Override
    public void responseReceived(Response response, int type) {
        mController.processResponse(response,type);
    }

    @Override
    public void showMap(int status, String longhitude, String latitude, String update) {

    }

    @Override
    public MenuHolder getCredentials() {
        return null;
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
}
