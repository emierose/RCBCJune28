package bluetooth.em.com.projectcountry.model;


import UnlitechDevFramework.src.ud.framework.data.Response;

/**
 * Created by Em on 5/6/2016.
 */

public interface ModelObserverInterface {
    /**
     * Returns when an error occurs in the model
     *
     * @param exception - contains the information of the error.
     */
    public void errorOnRequest(Exception exception);

    /**
     * Retuns a response object whenever a request is made from the model
     *
     * @param response - contains the information of the response
     * @param type
     */
    public void responseReceived(Response response, int type);
}