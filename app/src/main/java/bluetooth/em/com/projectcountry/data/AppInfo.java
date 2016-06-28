package bluetooth.em.com.projectcountry.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Em on 6/2/2016.
 */
public class AppInfo {
    private static AppInfo _instance;
    private String SHARED_PREFNAME = "RCBC_DATA";
    public static AppInfo getInstance() {
        if (_instance == null)
            _instance = new AppInfo();
        return _instance;
    }
    public SharedPreferences getSharedPreference(Context context) {
        return context.getSharedPreferences(SHARED_PREFNAME,
                Context.MODE_PRIVATE);
    }
}
