/* 
 *	Created by Ronald Phillip C. Cui Feb 18, 2014
 */
package UnlitechDevFramework.src.ud.framework.webservice.commands;

import java.io.IOException;
import java.util.ArrayList;

import UnlitechDevFramework.src.ud.framework.data.Response;
import UnlitechDevFramework.src.ud.framework.data.enums.Status;
import UnlitechDevFramework.src.ud.framework.webservice.NetworkTask.NetworkTaskListener;
import UnlitechDevFramework.src.ud.framework.webservice.data.WebServiceInfo;
import UnlitechDevFramework.src.ud.framework.webservice.interfaces.WebCommand;


public class UploadFilesCommand implements WebCommand {

    private ArrayList<WebServiceInfo> pendingUploads;
    private NetworkTaskListener mListener;
    private WebCommand command;

    public UploadFilesCommand(ArrayList<WebServiceInfo> pendingUploads) {
        this.pendingUploads = pendingUploads;
    }

    @Override
    public void setProgessListener(NetworkTaskListener listener) {
        // TODO Auto-generated method stub
        mListener = listener;
    }

    @Override
    public Response execute() throws IOException, Exception {
        // TODO Auto-generated method stub
        Response response = new Response();

        int fileIndex = 0;

        for (WebServiceInfo wsInfo : pendingUploads) {
            command = new UploadFileCommand(wsInfo);
            response = command.execute();

            if (response.getStatus() == Status.SUCCESS) {

                System.out.println("Response from upload: " + response.getResponse());
                if (mListener != null)
                    mListener.onProgressUpdate(1, fileIndex);

                fileIndex++;

            } else
                return response;
        }

        return response;
    }

    @Override
    public void cancel() {
        // TODO Auto-generated method stub
        if (command != null)
            command.cancel();
    }

}
