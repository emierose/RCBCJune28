package bluetooth.em.com.projectcountry.view;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import bluetooth.em.com.projectcountry.data.ClientObjects;

/**
 * Created by Em on 6/28/2016.
 */
public interface  PaymentOptionsView extends ViewInterface {
    void displayPayment(ArrayList<ClientObjects> paymentmodes);
    PeraPadalaHolder getCredentials(int type);
    class PeraPadalaHolder{
        public EditText cardno;
        public EditText ccv;
        public TextInputLayout tl_cardno;
        public TextInputLayout tl_ccv;
        public Spinner ex_month;
        public Spinner ex_year;
        public AlertDialog mAlertDialog;
    }
}
