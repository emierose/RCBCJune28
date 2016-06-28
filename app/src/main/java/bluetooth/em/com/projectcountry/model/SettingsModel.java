package bluetooth.em.com.projectcountry.model;

import android.content.Context;
import android.content.SharedPreferences;

import bluetooth.em.com.projectcountry.data.AppInfo;

/**
 * Created by Em on 6/2/2016.
 */
public class SettingsModel {
    public static final String NEW_INSTALL = "new_install";
    public static final String CURRENT_DB_VER = "curr_db_ver";
    public static final String REMEMBER_ME = "remember_me";
    /**
     * Saves the desired settings based on the setting name
     *
     * @param context - the application context
     * @param key     - the name of the setting
     * @param value   - the new int value of the setting
     */
    public void saveSetting(Context context, String key, int value) {
        SharedPreferences.Editor edit = AppInfo.getInstance().getSharedPreference(context).edit();
        edit.putInt(key, value);
        edit.commit();
    }
    /**
     * Saves the desired settings based on the setting name
     *
     * @param context - the application context
     * @param key     - the name of the setting
     * @param value   - the new boolean value of the setting
     */
    public void saveSetting(Context context, String key, boolean value) {
        SharedPreferences.Editor edit = AppInfo.getInstance().getSharedPreference(context).edit();
        edit.putBoolean(key, value);
        edit.commit();
    }

    /**
     * Checks if the application is newly installed return true or false
     *
     * @param context - the application context
     */
    public boolean isFirstInstall(Context context) {
        // TODO Auto-generated method stub
        return AppInfo.getInstance().getSharedPreference(context).getBoolean(NEW_INSTALL, true);
    }

    public int getLastestDBVer(Context context) {
        // TODO Auto-generated method stub
        return AppInfo.getInstance().getSharedPreference(context).getInt(CURRENT_DB_VER, 0);
    }

}
