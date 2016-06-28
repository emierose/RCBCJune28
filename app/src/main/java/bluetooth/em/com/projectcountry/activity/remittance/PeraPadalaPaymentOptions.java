package bluetooth.em.com.projectcountry.activity.remittance;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import UnlitechDevFramework.src.ud.framework.data.Response;
import bluetooth.em.com.projectcountry.R;
import bluetooth.em.com.projectcountry.controller.PaymentOptionsController;
import bluetooth.em.com.projectcountry.data.ClientObjects;
import bluetooth.em.com.projectcountry.data.Title;
import bluetooth.em.com.projectcountry.data.adapters.PadalaPaymentsAdapter;
import bluetooth.em.com.projectcountry.model.PeraPadalaModel;
import bluetooth.em.com.projectcountry.view.MenuHolder;
import bluetooth.em.com.projectcountry.view.PaymentOptionsView;

/**
 * Created by Em on 6/28/2016.
 */
public class PeraPadalaPaymentOptions extends AppCompatActivity implements PaymentOptionsView ,PeraPadalaModel.PeraPadalaModelObserver{
    Toolbar toolbar;
    PaymentOptionsController mController;
    PeraPadalaModel mModel;
    RecyclerView recList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remittance_paymentmodes);

        mModel = new PeraPadalaModel();
        mModel.registerObserver(this);
        mController = new PaymentOptionsController(this,mModel);
        mController.requestPaymentOptions();
        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent returnIntent = new Intent();
//        returnIntent.putExtra("position",1);
//        setResult(1,returnIntent);
//        finish();
                mController.addPaymentMode();
            }
        });
        System.out.println("PADALA 1" );
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        recList = (RecyclerView) findViewById(R.id.cardList);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

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
    public void displayPayment(ArrayList<ClientObjects> paymentmodes) {

        PadalaPaymentsAdapter adaper = new PadalaPaymentsAdapter(getActivity(),this, paymentmodes);
        recList.setAdapter(adaper);
//        Intent returnIntent = new Intent();
//        returnIntent.putExtra("position",position);
//        setResult(1,returnIntent);
//        finish();
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
        builder.setTitle(Title.PERAPADALASEND);
        builder.setMessage(message);
        builder.setPositiveButton("OK", null);
        builder.setOnDismissListener(listener);
        builder.show();
    }

    @Override
    public void showRetryError(String title, String message, DialogInterface.OnDismissListener listener) {

    }

    @Override
    public void secondResponseReceived(Response response, int type) {
        mController.processResponse(response, type);
    }

    @Override
    public void errorOnRequest(Exception exception) {

    }

    @Override
    public void responseReceived(Response response, int type) {

    }
}
