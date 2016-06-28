package bluetooth.em.com.projectcountry.view;

import android.text.Html;
import android.widget.EditText;

/**
 * Created by Em on 5/6/2016.
 */
public class Validate {
    public static boolean isEmpty(EditText string){
        if(string.getText().toString().equals("") ||string.getText().toString().length() == 0 ){
            return true;
        }else return false;
    }
    public static void showEdittextError (EditText edittext){
        edittext.setError(Html.fromHtml("<font color='black'>This field should not be empty.</font>"));
    }
}
