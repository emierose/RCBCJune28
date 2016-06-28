/* 
 *	Created by Ronald Phillip C. Cui Feb 3, 2014
 */
package UnlitechDevFramework.src.ud.framework.webservice;

import android.accounts.NetworkErrorException;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.UnknownHostException;

import UnlitechDevFramework.src.ud.framework.data.Response;
import UnlitechDevFramework.src.ud.framework.utilities.WebServiceUtil;
import bluetooth.em.com.projectcountry.R;

public abstract class NetworkTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {

    /* Messages */
    public static final String FAILED_TO_CONNECT = "Failed to connect to server";
    protected NetworkTaskListener mListener;
    private int mRequestId;
    private Context mContext;
    /* Exceptions */
    private Exception exception;
    private UnknownHostException unknownHostException;
    private IOException ioException;
    private OnUnknownHostExceptionListener uheListener;
    private OnIOExceptionListener ioeListener;
    private OnNetworkUnavailableListener neListener;
    private OnExceptionListener eListener;
    private boolean isAborted = false;
    private ProgressDialog mDialog;

    public NetworkTask(Context context, int requestId) {
        mRequestId = requestId;
        mContext = context;
    }

    public void setUnknownHostExceptionListener(OnUnknownHostExceptionListener l) {
        this.uheListener = l;
    }

    public void setIOExceptionListener(OnIOExceptionListener l) {
        this.ioeListener = l;
    }

    public void setOnNetworkUnavailableListener(OnNetworkUnavailableListener l) {
        this.neListener = l;
    }

    public void setOnExceptionListener(OnExceptionListener l) {
        eListener = l;
    }

    public boolean isAborted() {
        return isAborted;
    }

    public boolean isOnline() {
        return WebServiceUtil.isNetworkAvailable(mContext);
    }

    public void abort() {
        isAborted = true;
        cancel(true);
    }

    public void setProgressDialog(String title, String message) {
        mDialog = new ProgressDialog(mContext);
//        mDialog.setTitle(title);

        mDialog.setProgressStyle(R.style.AppCompatAlertDialogStyle);
        mDialog.setMessage(message);
        mDialog.setCancelable(false);
        mDialog.setIndeterminate(true);
    }

    public ProgressDialog getProgressDialog() {
        return mDialog;
    }

    public void setProgressDialog(ProgressDialog dialog) {
        mDialog = dialog;
    }

    public abstract void setNetworkTaskListener(NetworkTaskListener listener);

    protected void onPreExecute() {
        super.onPreExecute();

        if (mDialog != null) mDialog.show();

        if (!isOnline()) {
            if (mDialog != null) mDialog.dismiss();
            if (neListener != null)
                neListener.onNetworkException(new NetworkErrorException(OnNetworkUnavailableListener.NO_CONNECTION));
            else if (eListener != null)
                eListener.onException(new NetworkErrorException(OnNetworkUnavailableListener.NO_CONNECTION));
            abort();
        }
    }

    @Override
    protected Result doInBackground(Params... param) {
        // TODO Auto-generated method stub
        if (isCancelled()) {
            return null;
        }
        try {
            return doNetworkTask();
        } catch (UnknownHostException e) {
            unknownHostException = e;
            return null;
        } catch (IOException e) {
            ioException = e;
            return null;
        } catch (Exception e) {
            exception = e;
            return null;
        }
    }

    protected void onProgressUpdate(Progress... progress) {
    }

    protected void onPostExecute(Result result) {

        if (mDialog != null) mDialog.dismiss();

        super.onPostExecute(result);


        if (isCancelled() || isAborted()) {
            return;
        }

        if (ioException != null) {
            if (ioeListener != null)
                ioeListener.onIOException(ioException);
            else if (eListener != null)
                eListener.onException(ioException);
        } else if (unknownHostException != null) {
            if (uheListener != null)
                uheListener.onUnknownHostException(unknownHostException);
            else if (eListener != null)
                eListener.onException(unknownHostException);
        } else if (exception != null) {
            if (eListener != null)
                eListener.onException(exception);
        } else {
            onPostSuccess(result, mRequestId);
        }
    }

    public abstract Result doNetworkTask() throws Exception;

    public abstract void onPostSuccess(Result result, int requestId);

    public interface OnUnknownHostExceptionListener {
        void onUnknownHostException(UnknownHostException exception);
    }

    public interface OnIOExceptionListener {
        void onIOException(IOException exception);
    }

    public interface OnExceptionListener {
        void onException(Exception exception);
    }

    public interface OnNetworkUnavailableListener {
        String NO_CONNECTION = "No internet connection available";

        void onNetworkException(NetworkErrorException exception);
    }

    public interface NetworkTaskListener {
        void onProgressUpdate(Integer... progress);

        void onTaskComplete(Response response, int requestId);
    }


}
