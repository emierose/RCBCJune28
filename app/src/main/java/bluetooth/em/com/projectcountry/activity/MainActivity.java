package bluetooth.em.com.projectcountry.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import UnlitechDevFramework.src.ud.framework.data.Response;
import bluetooth.em.com.projectcountry.R;
import bluetooth.em.com.projectcountry.activity.more.Terms;
import bluetooth.em.com.projectcountry.activity.remittance.PeraPadalaSend;
import bluetooth.em.com.projectcountry.controller.MainmenuController;
import bluetooth.em.com.projectcountry.data.Message;
import bluetooth.em.com.projectcountry.data.Title;
import bluetooth.em.com.projectcountry.data.User;
import bluetooth.em.com.projectcountry.model.AppGeneralModel;
import bluetooth.em.com.projectcountry.model.UserModel;
import bluetooth.em.com.projectcountry.view.MenuHolder;
import bluetooth.em.com.projectcountry.view.MenuInterface;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AppGeneralModel.AppGeneralModelObserver, MenuInterface {
    AppGeneralModel mModel;
    MainmenuController mCOntroller;
    ProgressDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        User user = new UserModel().getCurrentUser(getActivity());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView name = (TextView) findViewById(R.id.tv_acctname);
        TextView id = (TextView) findViewById(R.id.tv_id);
        name.setText(user.getUsername());
        id.setText(user.getClientId());

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().clear();


        init();
    }

    private void init() {
        mModel = new AppGeneralModel();
        mModel.registerObserver(this);
        mCOntroller = new MainmenuController(this, mModel);
        mCOntroller.requestUserAccess();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else{
            int backStackEntryCount  = getSupportFragmentManager().getBackStackEntryCount();
            Fragment f = getSupportFragmentManager().findFragmentById(R.id.content_frame);
            System.out.println("BACK STACK COUNT:" + backStackEntryCount);
            if(f != null ) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.remove(getSupportFragmentManager().findFragmentById(R.id.content_frame)).commit();
//                fab.setVisibility(View.VISIBLE);
//                findViewById(R.id.main).setVisibility(View.VISIBLE);
                getSupportActionBar().setTitle(Title.APPTITLE);

            } else {
            finish();            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void onResume() {

        super.onResume();
        User user = new UserModel().getCurrentUser(this);

        if (user.getUserStatus() == User.NODATA) {
            finish();
            gotologinScreen();

        }

    }

    private void gotologinScreen() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        getActivity().startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            signout();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        String tag = " ";
        String name = item.getTitle().toString();
        System.out.println("NAME:" + name);
        Fragment fragment = null;
        if (name.equals("Location Update (GPS)")) {
            checkLocation();
        }
        if (name.equals("Pera Padala (Remittance)")) {
            fragment = new PeraPadalaSend();
            tag = "Pera Padala Send";
        }
        if(name.equals("View Merchants Locations")){
            viewMerchantsLocations();
        }
        if(name.equals("View Terms and Conditions")){
            fragment = new Terms();
            tag = "Terms and Conditions";
        }  if(name.equals("View Terms and Conditions")){
            fragment = new Terms();
            tag = "About Us";
        }if(name.equals("Log out")){
            signout();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        if (fragment != null) {
            findViewById(R.id.fab).setVisibility(View.GONE);
            findViewById(R.id.main_layout).setVisibility(View.GONE);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment, tag);
            ft.commit();
            getSupportActionBar().setTitle(tag);
        }
        return true;
    }

    private void viewMerchantsLocations() {
//        Intent intent = new Intent(getActivity(), MerchantLocationActivity.class);
//        startActivity(intent);
        mCOntroller.requestMerchantLocations();
    }

    private void checkLocation() {
//        Intent intent = new Intent(this,MapActivity.class);
//        startActivity(intent);
                mCOntroller.requestLastLocation();
    }

    @Override
    public void errorOnRequest(Exception exception) {
        showRetryError(Title.REGISTRATION, Message.EXCEPTION, new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mCOntroller.requestUserAccess();
            }
        });
    }

    @Override
    public void responseReceived(Response response, int type) {
        mCOntroller.processResponse(response);}


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
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.AppCompatAlertDialogStyle);
        builder.setMessage(message);
        builder.setPositiveButton("RETRY", null);
        builder.setOnDismissListener(listener);
        builder.show();
    }

    private void signout() {
        new UserModel() .signoutUser(MainActivity.this);
        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public MenuHolder getrCredentials() {
        MenuHolder holder = new MenuHolder();
        holder.navigationView = (NavigationView) findViewById(R.id.nav_view);
        return holder;
    }

    @Override
    public void secondResponseReceived(Response response, int type) {
        mCOntroller.processLocationResponse(response,type);
    }
}
