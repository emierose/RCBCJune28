package bluetooth.em.com.projectcountry.view;

/**
 * Created by Em on 6/8/2016.
 */
public interface  LocationView extends ViewInterface {
    void showMap(int status, String longhitude, String latitude, String update);
    MenuHolder getCredentials();
}
