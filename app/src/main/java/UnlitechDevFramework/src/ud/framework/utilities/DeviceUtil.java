/* 
 *	Created by Ronald Phillip C. Cui Jan 29, 2014
 */
package UnlitechDevFramework.src.ud.framework.utilities;

import android.content.Context;
import android.telephony.TelephonyManager;

public class DeviceUtil {

    /**
     * @param context - applications context
     * @return String value of the device ID
     * note: used to retrieve the device ID for devices. Cannot be used for devices that doesn't have phone functionalities
     */
    public static String getDeviceId(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }


}
