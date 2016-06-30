package bluetooth.em.com.projectcountry.activity.remittance;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Calendar;

import UnlitechDevFramework.src.ud.framework.data.Response;
import bluetooth.em.com.projectcountry.R;
import bluetooth.em.com.projectcountry.controller.PaymentOptionsController;
import bluetooth.em.com.projectcountry.data.ClientObjects;
import bluetooth.em.com.projectcountry.data.Title;
import bluetooth.em.com.projectcountry.data.adapters.PadalaPaymentsAdapter;
import bluetooth.em.com.projectcountry.model.PeraPadalaModel;
import bluetooth.em.com.projectcountry.view.PaymentOptionsView;

/**
 * Created by Em on 6/28/2016.
 */
public class PeraPadalaPaymentOptions extends AppCompatActivity implements PaymentOptionsView ,PeraPadalaModel.PeraPadalaModelObserver{
    Toolbar toolbar;
    PaymentOptionsController mController;
    PeraPadalaModel mModel;
    RecyclerView recList;
    AlertDialog.Builder    builder;
    private View dialogView;
    private AlertDialog mAlertDialog;

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
//                mController.addPaymentMode();
                displayPaymentRegistration();
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

    private void displayPaymentRegistration() {
        builder = new AlertDialog.Builder(getActivity(), R.style.AppCompatAlertDialogStyleBlue);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        dialogView = inflater.inflate(R.layout.remittance_add_creditcard, null);
        builder.setView(dialogView);
        Calendar cal = Calendar.getInstance();
//        final NumberPicker monthPicker = (NumberPicker) dialogView.findViewById(R.id.picker_month);
//        final NumberPicker yearPicker = (NumberPicker) dialogView.findViewById(R.id.picker_year);
//        monthPicker.setMinValue(1);
//        monthPicker.setMaxValue(12);
//        monthPicker.setValue(cal.get(Calendar.MONTH) + 1);
//
//        int year = cal.get(Calendar.YEAR);
//        yearPicker.setMinValue(year);
//        yearPicker.setMaxValue(2050);
//        yearPicker.setValue(year);
        Spinner month  = (Spinner)dialogView.findViewById(R.id.sp_month);
        Spinner year  = (Spinner)dialogView.findViewById(R.id.sp_year);
         String[] Months = new String[] { "Month","January", "February",
                "March", "April", "May", "June", "July", "August", "September",
                "October", "November", "December" };
        ArrayList<String> years = new ArrayList<String>();
        years.add("Year");
        for (int i = cal.get(Calendar.YEAR); i <= 2050; i++) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, years);
        year.setAdapter(adapter);
        ArrayAdapter<String> adapter2= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Months);
        month.setAdapter(adapter2);
        builder.setPositiveButton("REGISTER", null);
        builder.setNegativeButton("CANCEL", null);
        mAlertDialog = builder.create();
        mAlertDialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialog) {

                Button b = mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        // TODO Do something
                        mController.submitRegistration();
                    }
                });
            }
        });
        mAlertDialog.show(); }

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
    public PeraPadalaHolder getCredentials(int type) {
        PeraPadalaHolder holder = new PeraPadalaHolder();
        if(type ==2) {
            holder.cardno = (EditText) mAlertDialog.findViewById(R.id.et_cardno);
            holder.ccv = (EditText) mAlertDialog.findViewById(R.id.et_ccv);
            holder.tl_cardno = (TextInputLayout) mAlertDialog.findViewById(R.id.tl_cardno);
            holder.tl_ccv = (TextInputLayout) mAlertDialog.findViewById(R.id.tl_ccv);
            holder.ex_month = (Spinner) mAlertDialog.findViewById(R.id.sp_month);
            holder.ex_year = (Spinner) mAlertDialog.findViewById(R.id.sp_year);
            holder.mAlertDialog = mAlertDialog;
        }
        return holder;
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
