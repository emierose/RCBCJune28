/* 
 *	Created by Ronald Phillip C. Cui Feb 3, 2014
 */
package UnlitechDevFramework.src.ud.framework.webservice.interfaces;

import java.io.IOException;

import UnlitechDevFramework.src.ud.framework.data.Response;
import UnlitechDevFramework.src.ud.framework.webservice.NetworkTask.NetworkTaskListener;

public interface WebCommand {
    Response execute() throws IOException, Exception;

    void cancel();

    void setProgessListener(NetworkTaskListener listener);
}
