package bluetooth.em.com.projectcountry.view;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
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
    void displaySearchResult(ClientObjects searchData);
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
        public EditText amount;
        public TextInputLayout tl_amount;
        public TextView bene_id;
        public TextView sender_id;
        public View payment_data;
        public TextView payment_value;
        public TextView payment_id;
        public TextView sender_fullname;
        public TextView sender_bdate;
        public TextView sender_mobile;
        public TextView sender_email
                ;
        public TextView bene_fullname;
        public TextView bene_bdate;
        public TextView bene_mobile;
        public TextView bene_email;
        public TextView control_no;
        public TextView tv_amount;
        public TextView charge;
        public TextView date;
        public TextView origin;
        public Spinner idtype;
        public EditText idno;
        public TextInputLayout tl_idno;
        public TextView l_idtype;
    }
}
