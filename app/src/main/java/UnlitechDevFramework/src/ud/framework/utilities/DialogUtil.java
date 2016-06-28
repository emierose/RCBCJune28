/**
 * Created by MikoMorales on Feb 5, 2014
 * All rights reserved.
 */
package UnlitechDevFramework.src.ud.framework.utilities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public class DialogUtil {

    public static final String OK = "OK";
    public static final String CANCEL = "CANCEL";

    public static Dialog createErrorDialog(Context context, String title, String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(OK, null);

        return builder.create();

    }

    public static ProgressDialog createProgressDialog(Context context, String title, String message, int maxProgress) {

        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setCancelable(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setProgress(0);
        dialog.setMax(maxProgress);

        return dialog;

    }

    public static Dialog createConfirmationDialog(Context context, String title, String message, final OnClickListener listener) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setTitle(title);
        dialogBuilder.setMessage(message);
        dialogBuilder.setCancelable(false);
        dialogBuilder.setPositiveButton(OK, new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onClick(dialog, Dialog.BUTTON_POSITIVE);
            }
        });
        dialogBuilder.setNegativeButton(CANCEL, new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onClick(dialog, Dialog.BUTTON_NEGATIVE);
            }
        });

        return dialogBuilder.create();
    }
}
