/* 
 *	Created by Ronald Phillip C. Cui Feb 27, 2014
 */
package UnlitechDevFramework.src.ud.framework.webservice.commands;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

import UnlitechDevFramework.src.ud.framework.data.Response;
import UnlitechDevFramework.src.ud.framework.data.enums.Status;
import UnlitechDevFramework.src.ud.framework.utilities.WebServiceUtil;
import UnlitechDevFramework.src.ud.framework.webservice.NetworkTask;
import UnlitechDevFramework.src.ud.framework.webservice.NetworkTask.NetworkTaskListener;
import UnlitechDevFramework.src.ud.framework.webservice.data.WebServiceInfo;
import UnlitechDevFramework.src.ud.framework.webservice.interfaces.WebCommand;

public class PostCommand implements WebCommand {

    private WebServiceInfo wsInfo;
    private HttpPost post;

    public PostCommand(WebServiceInfo wsInfo) {
        // TODO Auto-generated constructor stub
        this.wsInfo = wsInfo;
    }


    public Response execute() throws IOException, Exception {
        // TODO Auto-generated method stub

        Response response = new Response();
        HttpEntity content = getHttpEntity();

        if (content != null) {

            String jsonContent = EntityUtils.toString(content);
            response.setStatus(Status.SUCCESS);
            response.setResponse(jsonContent);
            return response;

        }

        response.setStatus(Status.FAILED);
        response.setMessage(NetworkTask.FAILED_TO_CONNECT);
        return response;
    }

    public void cancel() {
        // TODO Auto-generated method stub
        if (post != null)
            post.abort();
    }

    public HttpEntity getHttpEntity() throws IOException, Exception {
        return WebServiceUtil.getHttpEntity(wsInfo, post);
    }

    @Override
    public void setProgessListener(NetworkTaskListener mListener) {
    }
}
