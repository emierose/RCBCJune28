package bluetooth.em.com.projectcountry.model;

import android.content.Context;

import org.json.JSONException;

import java.util.ArrayList;

import UnlitechDevFramework.src.ud.framework.data.Response;
import UnlitechDevFramework.src.ud.framework.webservice.NetworkTask;
import UnlitechDevFramework.src.ud.framework.webservice.WebServiceRequest;
import UnlitechDevFramework.src.ud.framework.webservice.commands.PostCommand;
import UnlitechDevFramework.src.ud.framework.webservice.data.WebServiceInfo;
import bluetooth.em.com.projectcountry.data.Message;
import bluetooth.em.com.projectcountry.data.User;
import bluetooth.em.com.projectcountry.view.ModelInterface;

/**
 * Created by Em on 6/1/2016.
 */
public class LogInModel implements ModelInterface {

    public ArrayList<LogInModelObserver> observerList = new ArrayList<LogInModelObserver>();

    @Override
    public void registerObserver(ModelObserverInterface observer) {
        // TODO Auto-generated method stub
        observerList.add((LogInModelObserver) observer);
    }

    @Override
    public void unregisterObserver(ModelObserverInterface observer) {
        // TODO Auto-generated method stub
        observerList.remove((LogInModelObserver) observer);
    }

    public void errorOnRequest(Exception exception) {
        for (LogInModelObserver observer : observerList) {
            observer.errorOnRequest(exception);
        }
    }

    protected void processResponse(Response response, int type) {
        for (LogInModelObserver observer : observerList) {
            observer.responseReceived(response, type);
        }
    }

    public void sendRequest(Context context, WebServiceInfo wsInfo, final int type) {
        // TODO Auto-generated method stub
        //The AsyncTask to call a Login API
//        if (wsInfo.getParam() != null) {
        WebServiceRequest request = new WebServiceRequest(context, 0, new PostCommand(wsInfo));

        //Add an exception listener when an unexpected exception occurred
        request.setOnExceptionListener(new NetworkTask.OnExceptionListener() {

            @Override
            public void onException(Exception exception) {
                // TODO Auto-generated method stub
                //Filter the message to replace the IP to "server"
                exception.printStackTrace();
                exception = new RuntimeException(Message.FAILED_TO_CONNECT );
                //Calls this method to notify observers
                //that there is an error on the request
                errorOnRequest(exception);
            }

        });
        request.setNetworkTaskListener(new NetworkTask.NetworkTaskListener() {

            @Override
            public void onProgressUpdate(Integer... progress) {
            }

            @Override
            public void onTaskComplete(Response response, int requestId) {
                // TODO Auto-generated method stub
                processResponse(response, type);
            }
        });

        request.execute();
//        }
//        else {
//            Toast.makeText(context, Message.ERROR, Toast.LENGTH_SHORT).show();
//        }
    }


    public interface LogInModelObserver extends ModelObserverInterface {
        public void loginSuccessful();
    }
    public void processUserData(Context context, Response response) throws JSONException {

        //Instantiates a user model object and delete the task
        //to transform the response to an application usable object.
        UserModel userModel = new UserModel();
        User user = userModel.processUserData(context, response);

        //Checks if the user is null or not
        //Tells the user model to save the user object and call login successful method
        if (user != null) {
            userModel.saveUser(context, user);
            loginSuccessful();
        }

    }
    public void loginSuccessful() {
        for (LogInModelObserver observer : observerList) {
            observer.loginSuccessful();
        }
    }
}