package bluetooth.em.com.projectcountry.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.dd.processbutton.iml.ActionProcessButton;

import java.util.ArrayList;

import UnlitechDevFramework.src.ud.framework.data.Response;
import bluetooth.em.com.projectcountry.R;
import bluetooth.em.com.projectcountry.controller.RegistrationController;
import bluetooth.em.com.projectcountry.data.CountryObjects;
import bluetooth.em.com.projectcountry.model.RegistrationModel;
import bluetooth.em.com.projectcountry.view.RegistrationHolder;
import bluetooth.em.com.projectcountry.view.RegistrationInterface;

/**
 * Created by Em on 5/11/2016.
 */
public class Registration extends AppCompatActivity implements RegistrationInterface,RegistrationModel.RegistrationModelObserver {
    RegistrationModel mModel;
    RegistrationController mController;
    public static ArrayList<CountryObjects> countryObjects = new ArrayList<CountryObjects>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_mainform);
        mModel = new RegistrationModel();
        mModel.registerObserver(this);
        mController = new RegistrationController(this,mModel);
        mController.requestCountry();
//        mController.requestBankBranches();

        findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mController.register();
            }
        });
        Animation animation;
        animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.anim_blink);
        RegistrationHolder holder = getregistrationCredentials();
        holder.text.startAnimation(animation);
        holder.tv_p_country.startAnimation(animation);
        holder.tv_p_state.startAnimation(animation);
        holder.tv_s_country.startAnimation(animation);
        holder.tv_s_state.startAnimation(animation);
        holder.tv_t_country.startAnimation(animation);
        holder.tv_t_state.startAnimation(animation);
        holder.tv_place_country.startAnimation(animation);
        holder.tv_place_state.startAnimation(animation);
        holder.tv_nationality.startAnimation(animation);
        holder.p_country.setOnItemSelectedListener(mController.on_p_country_select());
        holder.s_country.setOnItemSelectedListener(mController.on_s_country_select());
        holder.t_country.setOnItemSelectedListener(mController.on_t_country_select());
//        holder.bank.setOnItemSelectedListener(mController.on_bank_select());
        holder.bday_country.setOnItemSelectedListener(mController.on_bplace_country_select());

    }


//
//    private void validateForm() {
//        RegistrationHolder holder = getregistrationCredentials();
//
//        if(Validate.isEmpty(holder.username)){
//                Validate.showEdittextError(holder.username);
//        }else  if(Validate.isEmpty(holder.tpass)){
//            Validate.showEdittextError(holder.tpass);
//        }
//    }

    @Override
    public RegistrationHolder getregistrationCredentials() {
        RegistrationHolder holder = new RegistrationHolder();
        holder.submit = (ActionProcessButton) findViewById(R.id.btn_submit);
        holder.username = (EditText)findViewById(R.id.et_username);
        holder.tpass = (EditText)findViewById(R.id.et_password);
        holder.re_tpass = (EditText)findViewById(R.id.et_conf_password);
        holder.firstname = (EditText)findViewById(R.id.et_fname);
        holder.middlename = (EditText)findViewById(R.id.et_mname);
        holder.lastname = (EditText)findViewById(R.id.et_lname);
        holder.bdate = (EditText)findViewById(R.id.et_dob);
        holder.bday_country = (Spinner)findViewById(R.id.sp_bplace_country);
        holder.bday_state = (Spinner)findViewById(R.id.sp_bplace_state);
        holder.status = (Spinner)findViewById(R.id.sp_status);
        holder.mothername = (EditText)findViewById(R.id.et_mothername);
        holder.nationality = (Spinner)findViewById(R.id.sp_nationality);
        holder.tv_nationality = (TextView)findViewById(R.id.tv_nationality);
        holder.tin = (EditText)findViewById(R.id.et_tin_no);
        holder.sss = (EditText)findViewById(R.id.et_sss);
        holder.passport = (EditText)findViewById(R.id.et_passport);
        holder.acr = (EditText)findViewById(R.id.et_acr);
//        holder.cadrno = (EditText)findViewById(R.id.et_cardno);
//        holder.ccv = (EditText)findViewById(R.id.et_ccv);
        holder.p_add = (EditText)findViewById(R.id.et_p_address);
        holder.p_country = (Spinner)findViewById(R.id.sp_p_country);
        holder.p_state = (Spinner)findViewById(R.id.sp_p_state);
        holder.s_add = (EditText)findViewById(R.id.et_s_address);
        holder.s_country = (Spinner)findViewById(R.id.sp_s_country);
        holder.s_state = (Spinner)findViewById(R.id.sp_s_state);
        holder.t_add = (EditText)findViewById(R.id.et_t_address);
        holder.t_country = (Spinner)findViewById(R.id.sp_t_country);
        holder.t_state = (Spinner)findViewById(R.id.sp_t_state);
        holder.residential = (EditText)findViewById(R.id.et_residential);
        holder.office = (EditText)findViewById(R.id.et_office);
        holder.mobile = (EditText)findViewById(R.id.et_mobile);
        holder.p_email = (EditText)findViewById(R.id.et_p_email);
        holder.s_email = (EditText)findViewById(R.id.et_s_email);
        holder.t_email = (EditText)findViewById(R.id.et_t_email);
//        holder.branch = (Spinner)findViewById(R.id.sp_branch);
//        holder.bank = (Spinner)findViewById(R.id.sp_bank);
        holder.text = (TextView)findViewById(R.id.login);
        holder.tv_p_country = (TextView)findViewById(R.id.tv_l_p_country);
        holder.tv_p_state = (TextView)findViewById(R.id.tv_l_p_state);
        holder.tv_s_country = (TextView)findViewById(R.id.tv_l_s_country);
        holder.tv_s_state = (TextView)findViewById(R.id.tv_l_s_state);
        holder.tv_t_country = (TextView)findViewById(R.id.tv_l_t_country);
        holder.tv_t_state = (TextView)findViewById(R.id.tv_l_t_state);
        holder.tv_place_country = (TextView)findViewById(R.id.tv_l_bplace_country);
        holder.tv_place_state = (TextView)findViewById(R.id.tv_l_bplace_state);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.AppCompatAlertDialogStyle);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", null);
        builder.show();
    }

    @Override
    public void showRetryError(String title, String message, DialogInterface.OnDismissListener listener) {

    }

    @Override
    public void errorOnRequest(Exception exception) {
        RegistrationHolder holder = getregistrationCredentials();
//        holder.tv_p_country.setCompoundDrawables(getContext().getResources().getDrawable(R.drawable.ic_reload),null,null,null);
        holder.tv_p_country.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_reload, 0, 0, 0);
//        showError(Title.REGISTRATION, Message.EXCEPTION, null);
    }

    @Override
    public void responseReceived(Response response, int type) {

        mController.processResponse(response, type);
    }
    @Override
    public void processRegistration(Response response, int type) {
            mController.processRegistrationResponse(response,type);
    }
}
