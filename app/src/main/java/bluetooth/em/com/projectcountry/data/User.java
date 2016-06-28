package bluetooth.em.com.projectcountry.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Em on 6/1/2016.
 */
public class User {

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {

        @Override
        public User createFromParcel(Parcel source) {
            // TODO Auto-generated method stub
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            // TODO Auto-generated method stub
            return new User[size];
        }

    };
    public static int HASDATA = 1;
    public static int NODATA = 0;
    private int USER_STATUS = NODATA;
    private String USERNAME = "";
    private String CLIENTID = "";
    private String SESSIONID = "";
    public User() {
    }

    public User(Parcel in) {
        USERNAME = in.readString();
        CLIENTID = in.readString();
        SESSIONID = in.readString();
    }
    public int getUserStatus() {
        return USER_STATUS;
    }
    public void setUserStatus(int status) {
        USER_STATUS = status;
    }
    public String getUsername() {
        return USERNAME;
    }
    public void setUsername(String username) {
        this.USERNAME = username;
    }
    public String getClientId() {
        return CLIENTID;
    }
    public void setClientId(String clientid) {
        this.CLIENTID = clientid;
    }
    public String getSessionId() {
        return SESSIONID;
    }
    public void setSessionId(String sessionId) {
        this.SESSIONID = sessionId;
    }
}
