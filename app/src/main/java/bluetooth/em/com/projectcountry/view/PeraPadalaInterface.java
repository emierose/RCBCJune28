package bluetooth.em.com.projectcountry.view;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import bluetooth.em.com.projectcountry.data.ClientObjects;

/**
 * Created by Em on 6/16/2016.
 */
public interface PeraPadalaInterface  extends ViewInterface{
    PeraPadalaHolder getCredentials(int type);
    void showRegistrationDialog(String searchtype);
    void showSearchResultDialo(ArrayList<ClientObjects> searchData,String type);
    class PeraPadalaHolder {
        public EditText firstname;
        public EditText middlename;
        public EditText lastname;
        public EditText bdate;
        public  EditText mobile;
        public EditText email;

        public TextInputLayout tl_firstname;
        public TextInputLayout tl_middlename;
        public TextInputLayout tl_lastname;
        public TextInputLayout tl_bdate;
        public  TextInputLayout tl_mobile;
        public TextInputLayout tl_email;
        public AlertDialog builder;
        public View sender_data;
        public View bene_data;
        public AlertDialog searchDialog;
        public TextView payment_option;
        public CardView bene_layout;
    }
}
