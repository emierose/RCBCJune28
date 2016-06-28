package UnlitechDevFramework.src.ud.framework.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

import UnlitechDevFramework.src.ud.framework.data.enums.AppMode;

public abstract class EngineAppInfo {

    /* The SENDER ID For Push Notification */
    private static String SENDER_ID = "";

    /* Application Configurations */
    private AppMode APP_MODE = AppMode.DEV;

    /* slash screen timeout config */
    private long TIMEOUT = 3000;

    /* name of the shared preference of the app */
    private String SHARED_PREF_NAME;

    /* Server Urls */
    private String DEV_URL;
    private String LIVE_URL;
    private String BACKUP_URL;
    private String DEMO_URL;

    private String BASE_PATH = "";
    private String OUTBOX = BASE_PATH + "outbox/";
    private String SENT = BASE_PATH + "sent/";
    private String DATA = BASE_PATH + "data/";

    private String appUpdateUrl;

    public long getTimeout() {
        return TIMEOUT;
    }

    /* Getter and Setter for timeout ex. Splash Screen */
    public void setTimeout(long timeout) {
        TIMEOUT = timeout;
    }

    public AppMode getAppMode() {
        return APP_MODE;
    }

    /* Getter and Setter for App Mode */
    public void setAppMode(AppMode appMode) {
        APP_MODE = appMode;
    }

    /* Getter and Setter for Base URL */
    public void setBaseUrl(AppMode appMode, String url) {
        switch (appMode) {
            case LIVE:
                LIVE_URL = url;
                break;
            case BACKUP:
                BACKUP_URL = url;
                break;
            case DEMO:
                DEMO_URL = url;
                break;
            case DEV:
            default:
                DEV_URL = url;
                break;
        }
    }

    /**
     * Gets the base url set based on ServerType
     *
     * @return url in String format.
     */
    private String getBaseUrl() {
        switch (APP_MODE) {
            case DEV:
                return DEV_URL;
            case LIVE:
                return LIVE_URL;
            case BACKUP:
                return BACKUP_URL;
            case DEMO:
                return DEMO_URL;
            default:
                return DEV_URL;
        }
    }

    /**
     * Get the url of the webservice with the corresponding function.
     *
     * @param function - function to be called based on the given base url.
     * @return the complete url for a specific web service
     */
    public String getUrl(String function) {
        return getBaseUrl().concat(function);
    }

    public void setAppUpdateUrl(String updateUrl) {
        appUpdateUrl = updateUrl;
    }

    public String setAppUpdateUrl() {
        return appUpdateUrl;
    }

    public String getPreferenceName() {
        return SHARED_PREF_NAME;
    }

    /* Setter and Getter for Shared Preferences */
    public void setPreferenceName(String prefName) {
        SHARED_PREF_NAME = prefName;
    }

    public SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(getPreferenceName(), Context.MODE_PRIVATE);
    }

    public String getSenderID() {
        // TODO Auto-generated method stub
        return SENDER_ID;
    }

    public void setSenderID(String senderID) {
        SENDER_ID = senderID;
    }

    public void setBasePath(Context context) {
        BASE_PATH = Environment.getExternalStorageDirectory() + "/Android/data/" + context.getPackageName() + "/files/";
    }

    public String getOutboxPath() {
        return OUTBOX;
    }

    public String getSentPath() {
        return SENT;
    }

    public String getDataPath() {
        return DATA;
    }
}
