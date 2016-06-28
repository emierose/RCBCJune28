/* 
 *	Created by Ronald Phillip C. Cui Jan 30, 2014
 */
package UnlitechDevFramework.src.ud.framework.utilities;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewUtil {

    /* EditText input validation */
    public static boolean isEmpty(EditText... editTexts) {

        for (EditText et : editTexts) {
            if (et == null || et.getText().toString().trim().length() == 0)
                return true;
        }

        return false;

    }

    public static boolean isAlphaNumeric(EditText... editTexts) {

        for (EditText et : editTexts) {
            if (et == null || !et.getText().toString().trim().matches("^[a-zA-Z0-9.,]+$"))
                return false;
        }

        return true;

    }

    /* DP to Pixels converter */
    public static int dpTopx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    public static TextView getTextView(Activity activity, int resId) {
        return ((TextView) activity.findViewById(resId));
    }

    public static EditText getEditText(Activity activity, int resId) {
        return ((EditText) activity.findViewById(resId));
    }

    public static ArrayList<EditText> getEditText(Activity activity, Integer... resIds) {
        ArrayList<EditText> etList = new ArrayList<EditText>();

        for (int resId : resIds) {
            etList.add((EditText) activity.findViewById(resId));
        }

        return etList;
    }

    public static ImageView getImageView(Activity activity, int resId) {
        return ((ImageView) activity.findViewById(resId));
    }

    public static Button getButton(Activity activity, int resId) {
        return ((Button) activity.findViewById(resId));
    }

    public static ImageButton getImageButton(Activity activity, int resId) {
        return ((ImageButton) activity.findViewById(resId));
    }

    public static CheckBox getCheckBox(Activity activity, int resId) {
        return ((CheckBox) activity.findViewById(resId));
    }

    public static RadioButton getRadioButton(Activity activity, int resId) {
        return ((RadioButton) activity.findViewById(resId));
    }

    public static void showButtons(Activity activity, int id) {
        // TODO Auto-generated method stub
        activity.findViewById(id).setVisibility(View.VISIBLE);
    }

    public static void hideButton(Activity activity, int id) {
        // TODO Auto-generated method stub
        activity.findViewById(id).setVisibility(View.INVISIBLE);
    }
}
