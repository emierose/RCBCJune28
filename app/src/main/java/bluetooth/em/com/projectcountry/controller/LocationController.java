package bluetooth.em.com.projectcountry.controller;

import org.json.JSONException;
import org.json.JSONObject;

import UnlitechDevFramework.src.ud.framework.data.Response;
import UnlitechDevFramework.src.ud.framework.data.enums.Status;
import UnlitechDevFramework.src.ud.framework.webservice.data.WebServiceInfo;
import bluetooth.em.com.projectcountry.data.Constants;
import bluetooth.em.com.projectcountry.data.JSONFlag;
import bluetooth.em.com.projectcountry.data.Message;
import bluetooth.em.com.projectcountry.data.Title;
import bluetooth.em.com.projectcountry.data.User;
import bluetooth.em.com.projectcountry.model.AppGeneralModel;
import bluetooth.em.com.projectcountry.model.UserModel;
import bluetooth.em.com.projectcountry.view.LocationView;
import bluetooth.em.com.projectcountry.view.MenuHolder;

/**
 * Created by Em on 6/8/2016.
 */
public class LocationController {
    LocationView mView;
    AppGeneralModel mModel;
    public LocationController(LocationView view, AppGeneralModel model) {
        mView = view;
        mModel = model;
    }

    public void processResponse(Response response, int type) {
        System.out.println("LOCATION:"+response.getResponse());
        try {
            if (response.getStatus() == Status.SUCCESS) {
                JSONObject jo = new JSONObject(response.getResponse());

                if (jo.getString(JSONFlag.STATUS).equals(JSONFlag.SUCCESSFUL)) {
                    MenuHolder holder = mView.getCredentials();
                    holder.btnUpdateLoc.setProgress(100);
                    holder.btnUpdateLoc.setEnabled(false);
                        mView.showMap(0,"","","");
//                    JSONObject jo2 = jo.getJSONObject("result_data");
//                    if(jo2.getInt("result_data") == 1) {
//                        String longitude = jo.getString("longitude");
//                        String latitude = jo.getString("latitude");
//                        String last_update = jo.getString("last_updated");
//                        mView.showMap(1,longitude,latitude,last_update);
//                    }else {
//                            mView.showMap(0,"","","");
//
//                    }
                } else {
                    MenuHolder holder = mView.getCredentials();
                    holder.btnUpdateLoc.setProgress(0);
                    mView.showError(Title.REGISTRATION, jo.getString(JSONFlag.MESSAGE), null);
                }
            } else {
                mView.showError(Title.REGISTRATION, Message.ERROR_FETCHING_DATA, null);
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            mView.showError("", Message.RUNTIME_ERROR, null);
        } catch (JSONException e) {
            e.printStackTrace();
            mView.showError("", Message.JSON_ERROR, null);
        }
    }

    public void updateLocation() {
        try {
            System.out.println("SENDING");
           MenuHolder holder = mView.getCredentials();
            holder.btnUpdateLoc.setProgress(1);
            User user= new UserModel().getCurrentUser(mView.getContext());
            WebServiceInfo wsInfo = new WebServiceInfo("http://52.77.224.133:8089/ws_user/update_merchant_location");
            wsInfo.addParam("client_id",user.getClientId());
            wsInfo.addParam("session_id",user.getSessionId());
            wsInfo.addParam("longitude", String.valueOf(Constants.LONGITUDE));
            wsInfo.addParam("latitude", String.valueOf(Constants.LATITUDE));
            mModel.sendRequest(mView.getContext(), wsInfo, 1);
        } catch (Exception e) {
        }
    }
}
