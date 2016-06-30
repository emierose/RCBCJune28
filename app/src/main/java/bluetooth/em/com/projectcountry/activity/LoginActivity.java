package bluetooth.em.com.projectcountry.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.dd.processbutton.iml.ActionProcessButton;

import UnlitechDevFramework.src.ud.framework.data.Response;
import bluetooth.em.com.projectcountry.R;
import bluetooth.em.com.projectcountry.controller.LoginController;
import bluetooth.em.com.projectcountry.data.Message;
import bluetooth.em.com.projectcountry.data.Title;
import bluetooth.em.com.projectcountry.data.User;
import bluetooth.em.com.projectcountry.model.LogInModel;
import bluetooth.em.com.projectcountry.model.SettingsModel;
import bluetooth.em.com.projectcountry.model.UserModel;
import bluetooth.em.com.projectcountry.view.RegistrationHolder;
import bluetooth.em.com.projectcountry.view.RegistrationInterface;
/**
 * Created by Em on 5/3/2016.
 */
public class LoginActivity  extends AppCompatActivity implements View.OnClickListener, RegistrationInterface,LogInModel.LogInModelObserver  {
   LoginController mController;
    LogInModel mModel;
    private SettingsModel mModel2;
    ActionProcessButton btnSignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        /* Provides a 3 sec delay before calling the init() method */

                init();

        mModel = new LogInModel();
        mModel.registerObserver(this);
        mController = new LoginController(this, mModel);

        findViewById(R.id.tv_create_account).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, Registration.class);
                startActivity(intent);
            }
        });
        btnSignIn = (ActionProcessButton) findViewById(R.id.btn_login);
        btnSignIn.setMode(ActionProcessButton.Mode.ENDLESS);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegistrationHolder holder = getregistrationCredentials();
                holder.username.setError(null);
                holder.tpass.setError(null);
//                btnSignIn.setProgress(1);
                mController.login();
            }
        });
    }

    /* Initializes the data required by the activity */
    private void init() {

        mModel2 = new SettingsModel(); // initialized the Settings Model
        User user = new UserModel().getCurrentUser(this);

        //Checks if the user object has a valid user data

        //display main menu screen when user object is a valid user data
        if (user.getUserStatus() == User.HASDATA)
            loginSuccessful();
            //display login screen when user object is not a valid user data
//        else
//			showLoginScreen();
//            showMainScreen();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_create_account:
//                Intent intent = new Intent(LoginActivity.this, Registration.class);
//                startActivity(intent);

                break;

        }
    }

    @Override
    public RegistrationHolder getregistrationCredentials() {
        RegistrationHolder holder = new RegistrationHolder();
        holder.username = (EditText)findViewById(R.id.et_username);
        holder.tpass = (EditText)findViewById(R.id.et_password);
        holder.lo_username = (TextInputLayout)findViewById(R.id.lo_username);
        holder.lo_password = (TextInputLayout)findViewById(R.id.lo_password);
        holder.btnSignIn = (ActionProcessButton) findViewById(R.id.btn_login);
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
        showError(Title.REGISTRATION, Message.EXCEPTION,null);
    }

    @Override
    public void responseReceived(Response response, int type) {
        mController.processLoginResponse(response);
    }

    @Override
    public void loginSuccessful() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
         getActivity().startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
    finish();    }
}
