/* 
 *	Created by Ronald Phillip C. Cui Feb 3, 2014
 */
package UnlitechDevFramework.src.ud.framework.data;


import UnlitechDevFramework.src.ud.framework.data.enums.Status;

public class Response {

    private Status mStatus = Status.FAILED;
    private String mResponse = null;
    private String mMessage = "";

    public Status getStatus() {
        return mStatus;
    }

    public void setStatus(Status status) {
        mStatus = status;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public String getResponse() {
        return mResponse;
    }

    public void setResponse(String response) {
        // TODO Auto-generated method stub
        mResponse = response;
    }


}
