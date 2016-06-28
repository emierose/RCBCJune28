package bluetooth.em.com.projectcountry.model;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import UnlitechDevFramework.src.ud.framework.data.Response;
import bluetooth.em.com.projectcountry.data.AppInfo;
import bluetooth.em.com.projectcountry.data.JSONFlag;
import bluetooth.em.com.projectcountry.data.User;

/**
 * Created by Em on 6/1/2016.
 */
public class UserModel {
    public static final String USERNAME = "USERNAME";
    public static final String CLIENTID = "CLIENTID";
    public static final String SESSIONID= "SESSIONID";
    public static final String USER_STATUS = "USERSTATUS";
    public User getCurrentUser(Context context) {
        //The user data source
        SharedPreferences sp = AppInfo.getInstance().getSharedPreference(context);
        /* Initialization of the user object */
        User user = new User();

		/* Retrieving the user object from the data source 	*/
        user.setUsername(sp.getString(USERNAME, ""));
        user.setClientId(sp.getString(CLIENTID, ""));
        user.setSessionId(sp.getString(SESSIONID, ""));
        user.setUserStatus(sp.getInt(USER_STATUS, User.NODATA));
        return user;
    }
    /**
     * saved the user object to the data source
     *
     * @param context - context of the application
     * @param user    - object to store in the data source
     */
    public void saveUser(Context context, User user) {
        // TODO Auto-generated method stub
        //Create an editor instance to update the data source
        SharedPreferences.Editor edit = AppInfo.getInstance().getSharedPreference(context).edit();
        edit.putString(USERNAME, user.getUsername());
        edit.putString(CLIENTID, user.getClientId());
        edit.putString(SESSIONID, user.getSessionId());
        edit.putInt(USER_STATUS, user.getUserStatus());
        edit.commit(); //saves changes to the data source
    }

    /**
     * process the API response and store it to a user object
     *
     * @param context  - application context
     * @param response - response object of the API request
     * @return returns a user object based from the response
     * @throws RuntimeException
     * @throws JSONException
     */
    public User processUserData(Context context, Response response) throws RuntimeException, JSONException {
        // TODO Auto-generated method stub

        //create a jo object from the api response
        JSONObject jo = new JSONObject(response.getResponse());
        JSONObject json = jo.getJSONObject("result_data");
            User user = new User();
            user.setUsername(json.getString(JSONFlag.USERNAME));
            user.setClientId(json.getString(JSONFlag.CLICNETID));
            user.setSessionId(json.getString(JSONFlag.SESSIONID));
            user.setUserStatus(User.HASDATA);
        return user;

    }
    /**
     * unloads all the user data before signing out
     *
     * @param context - application context
     */
    public void signoutUser(Context context) {
        // TODO Auto-generated method stub
        //Create editor instance to edit data source
        SharedPreferences sp = AppInfo.getInstance().getSharedPreference(context);
        SharedPreferences.Editor edit = AppInfo.getInstance().getSharedPreference(context).edit();
        User user = new User();
        edit.putString(USERNAME, user.getUsername());
        edit.putString(CLIENTID, user.getClientId());
        edit.putString(SESSIONID, user.getSessionId());
        edit.putInt(USER_STATUS, user.getUserStatus());
        new SettingsModel().saveSetting(context, SettingsModel.REMEMBER_ME, false);
        edit.commit(); //save changes made to the data source
    }
}
