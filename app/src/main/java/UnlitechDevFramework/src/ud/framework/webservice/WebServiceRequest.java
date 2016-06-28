/* 
 *	Created by Ronald Phillip C. Cui Mar 3, 2014.
 */
package UnlitechDevFramework.src.ud.framework.webservice;

import android.content.Context;

import java.io.IOException;

import UnlitechDevFramework.src.ud.framework.data.Response;
import UnlitechDevFramework.src.ud.framework.webservice.interfaces.WebCommand;

public class WebServiceRequest extends NetworkTask<Void, Void, Response> {

    WebCommand command;

    public WebServiceRequest(Context context, int requestId, WebCommand command) {
        super(context, requestId);
        this.command = command;
    }

    public void abort() {
        if (command != null) command.cancel();
        super.abort();
    }

    @Override
    public void setNetworkTaskListener(NetworkTaskListener listener) {
        mListener = listener;
    }

    @Override
    public Response doNetworkTask() throws IOException, Exception {
        command.setProgessListener(mListener);
        return command.execute();
    }

    @Override
    public void onPostSuccess(Response result, int requestId) {
        if (mListener != null)
            mListener.onTaskComplete(result, requestId);
    }

}