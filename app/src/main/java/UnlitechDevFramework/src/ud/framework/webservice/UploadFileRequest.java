/* 
 *	Created by Ronald Phillip C. Cui Feb 13, 2014
 */
package UnlitechDevFramework.src.ud.framework.webservice;

import android.content.Context;

import UnlitechDevFramework.src.ud.framework.data.Response;
import UnlitechDevFramework.src.ud.framework.webservice.interfaces.WebCommand;


public class UploadFileRequest extends NetworkTask<Void, Void, Response> {


    private WebCommand command;

    public UploadFileRequest(Context context, int requestId, WebCommand command) {
        super(context, requestId);
        this.command = command;
        // TODO Auto-generated constructor stub
    }

    public void abort() {
        if (command != null) command.cancel();
        super.abort();
    }

    @Override
    public void setNetworkTaskListener(NetworkTaskListener listener) {
        // TODO Auto-generated method stub
        mListener = listener;
    }

    @Override
    public Response doNetworkTask() throws Exception {
        // TODO Auto-generated method stub
        command.setProgessListener(mListener);
        return command.execute();
    }

    @Override
    public void onPostSuccess(Response result, int requestId) {
        if (mListener != null)
            mListener.onTaskComplete(result, requestId);
    }

}
