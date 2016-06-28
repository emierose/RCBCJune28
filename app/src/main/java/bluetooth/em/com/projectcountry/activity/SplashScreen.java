package bluetooth.em.com.projectcountry.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.skyfishjy.library.RippleBackground;

import bluetooth.em.com.projectcountry.R;
import bluetooth.em.com.projectcountry.data.User;
import bluetooth.em.com.projectcountry.model.SettingsModel;
import bluetooth.em.com.projectcountry.model.UserModel;

/**
 * Created by Em on 6/3/2016.
 */
public class SplashScreen extends Activity {
    RippleBackground rippleBackground;
    private SettingsModel mModel2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        rippleBackground=(RippleBackground)findViewById(R.id.content);
        rippleBackground.startRippleAnimation();
        /* Provides a 3 sec delay before calling the init() method */
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                init();
            }
        }, 3000);
    }

    private void init() {
        mModel2 = new SettingsModel(); // initialized the Settings Model
        User user = new UserModel().getCurrentUser(this);

        //Checks if the user object has a valid user data

        //display main menu screen when user object is a valid user data
        if (user.getUserStatus() == User.HASDATA)
            showMainMenu();
            //display login screen when user object is not a valid user data
        else {
            showLoginScreen();
        }

        rippleBackground.stopRippleAnimation();
    }

    private void showLoginScreen() {
        Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void showMainMenu() {
        Intent intent = new Intent(SplashScreen.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
