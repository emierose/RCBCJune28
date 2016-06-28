package bluetooth.em.com.projectcountry.view;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by Em on 5/6/2016.
 */
public interface ViewInterface {
    /**
     * Get Views context
     */
    public Context getContext();

    /**
     * Get Views getActivity object
     */
    public Activity getActivity();

    /**
     * Call View to display error message
     */
    public void showError(String title, String message, DialogInterface.OnDismissListener listener);
    public void showRetryError(String title, String message, DialogInterface.OnDismissListener listener);
}
