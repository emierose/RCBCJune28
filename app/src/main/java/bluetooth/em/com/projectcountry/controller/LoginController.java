package bluetooth.em.com.projectcountry.controller;

import org.json.JSONException;
import org.json.JSONObject;

import UnlitechDevFramework.src.ud.framework.data.Response;
import UnlitechDevFramework.src.ud.framework.data.enums.Status;
import UnlitechDevFramework.src.ud.framework.utilities.ViewUtil;
import UnlitechDevFramework.src.ud.framework.webservice.data.WebServiceInfo;
import bluetooth.em.com.projectcountry.data.JSONFlag;
import bluetooth.em.com.projectcountry.data.Message;
import bluetooth.em.com.projectcountry.data.Title;
import bluetooth.em.com.projectcountry.model.LogInModel;
import bluetooth.em.com.projectcountry.view.RegistrationHolder;
import bluetooth.em.com.projectcountry.view.RegistrationInterface;

/**
 * Created by Em on 6/1/2016.
 */
public class LoginController {
    RegistrationInterface mView;
    LogInModel mModel;
    RegistrationHolder holder;

    public LoginController(RegistrationInterface view, LogInModel model) {
        mView = view;
        mModel = model;
    }
    public void login() {
        //Checks if the credentials are valid then calls the loginToServer method
        if (validCredentials())

            sendRequest();
    }
    public boolean validCredentials() {
     holder = mView.getregistrationCredentials();
        boolean result = true;

        if (ViewUtil.isEmpty(holder.username)) {
            holder.lo_username.setError("Username should not be empty.");
            result = false;
        }else if(ViewUtil.isEmpty(holder.tpass)) {
            holder.lo_password.setError("Password should not be empty.");
            result = false;
        }
//        if(message != null){
//            mView.showError(Title.LOGIN,message, null);
//            result = false;
//        }
        return result;

    }
    public void sendRequest() {
        try {
            holder.btnSignIn.setProgress(1);
            WebServiceInfo wsInfo = new WebServiceInfo("http://52.77.224.133:8089/ws_user/user_login");
            wsInfo.addParam("username",holder.username.getText().toString());
            wsInfo.addParam("password",holder.tpass.getText().toString());
            mModel.sendRequest(mView.getContext(), wsInfo, 1);
        } catch (Exception e) {
        }
    }

    public void processLoginResponse(Response response) {
     System.out.println("RESPONSE:"+response.getResponse());
        try {
            if (response.getStatus() == Status.SUCCESS) {
                JSONObject jo = new JSONObject(response.getResponse());
                if (jo.getString(JSONFlag.STATUS).equals(JSONFlag.SUCCESSFUL)) {
                    mModel.processUserData(mView.getContext(), response);
                    holder.btnSignIn.setProgress(100);
                } else {
                    mView.showError(Title.REGISTRATION, jo.getString(JSONFlag.MESSAGE), null);
                    holder.btnSignIn.setProgress(0);
//                    holder.btnSignIn.setError(jo.getString(JSONFlag.MESSAGE));
                }
            } else {
                mView.showError(Title.REGISTRATION, Message.ERROR_FETCHING_DATA, null);
                holder.btnSignIn.setProgress(0);
//                holder.btnSignIn.setError( Message.ERROR_FETCHING_DATA);
            }
        } catch (RuntimeException e) {
            mView.showError("", Message.RUNTIME_ERROR, null);
            e.printStackTrace();
            holder.btnSignIn.setProgress(0);
//            holder.btnSignIn.setError(Message.RUNTIME_ERROR);
        } catch (JSONException e) {
            mView.showError("", Message.JSON_ERROR, null);
            e.printStackTrace();
            holder.btnSignIn.setProgress(0);
//            holder.btnSignIn.setError(Message.JSON_ERROR);
        }

    }
}
