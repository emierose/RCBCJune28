package bluetooth.em.com.projectcountry.model;

import android.content.Context;

import java.util.ArrayList;

import UnlitechDevFramework.src.ud.framework.data.Response;
import UnlitechDevFramework.src.ud.framework.webservice.NetworkTask;
import UnlitechDevFramework.src.ud.framework.webservice.WebServiceRequest;
import UnlitechDevFramework.src.ud.framework.webservice.commands.PostCommand;
import UnlitechDevFramework.src.ud.framework.webservice.data.WebServiceInfo;
import bluetooth.em.com.projectcountry.data.Message;
import bluetooth.em.com.projectcountry.data.Title;
import bluetooth.em.com.projectcountry.view.ModelInterface;

/**
 * Created by Em on 5/11/2016.
 */
public class RegistrationModel implements ModelInterface {

    public ArrayList<RegistrationModelObserver> observerList = new ArrayList<RegistrationModelObserver>();

    @Override
    public void registerObserver(ModelObserverInterface observer) {
        // TODO Auto-generated method stub
        observerList.add((RegistrationModelObserver) observer);
    }

    @Override
    public void unregisterObserver(ModelObserverInterface observer) {
        // TODO Auto-generated method stub
        observerList.remove((RegistrationModelObserver) observer);
    }

    public void errorOnRequest(Exception exception) {
        for (RegistrationModelObserver observer : observerList) {
            observer.errorOnRequest(exception);
        }
    }

    protected void processSpecialPlancodes(Response response, int type) {
        for (RegistrationModelObserver observer : observerList) {
            observer.responseReceived(response, type);
        }
    }
    protected void processRegistration(Response response, int type) {
        for (RegistrationModelObserver observer : observerList) {
            observer.processRegistration(response, type);
        }
    }
    public void registrationRequest(Context context, WebServiceInfo wsInfo, final int type) {
        // TODO Auto-generated method stub
        //The AsyncTask to call a Login API
//        if (wsInfo.getParam() != null) {
        WebServiceRequest request = new WebServiceRequest(context, 0, new PostCommand(wsInfo));

        //Display a dialog box while the request is being processed
        request.setProgressDialog(Title.LOADING, Message.PLEASE_WAIT);

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
                processRegistration(response, type);
            }
        });

        request.execute();
//        }
//        else {
//            Toast.makeText(context, Message.ERROR, Toast.LENGTH_SHORT).show();
//        }
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
                    processSpecialPlancodes(response,type);
                }
            });

            request.execute();
//        }
//        else {
//            Toast.makeText(context, Message.ERROR, Toast.LENGTH_SHORT).show();
//        }
    }


    public interface RegistrationModelObserver extends ModelObserverInterface {
           public void processRegistration(Response response, int type);
    }

}
