package bluetooth.em.com.projectcountry.controller;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import UnlitechDevFramework.src.ud.framework.data.Response;
import UnlitechDevFramework.src.ud.framework.data.enums.Status;
import UnlitechDevFramework.src.ud.framework.webservice.data.WebServiceInfo;
import bluetooth.em.com.projectcountry.data.JSONFlag;
import bluetooth.em.com.projectcountry.data.MerchantLocationData;
import bluetooth.em.com.projectcountry.data.Message;
import bluetooth.em.com.projectcountry.data.Title;
import bluetooth.em.com.projectcountry.data.User;
import bluetooth.em.com.projectcountry.model.AppGeneralModel;
import bluetooth.em.com.projectcountry.model.UserModel;
import bluetooth.em.com.projectcountry.view.LocationView;

/**
 * Created by Em on 6/23/2016.
 */
public class MerchantLocationController {
    AppGeneralModel mModel;
    LocationView mView;
    ArrayList<MerchantLocationData> merchantData;
    public MerchantLocationController(LocationView view, AppGeneralModel model) {
        mModel = model;
        mView = view;
        merchantData = new ArrayList<>();
    }
    public void requestMerchantLocation() {
        try {
            User user= new UserModel().getCurrentUser(mView.getContext());
            WebServiceInfo wsInfo = new WebServiceInfo("http://52.77.224.133:8089/ws_user/fetch_all_merchant_location");
//            WebServiceInfo wsInfo = new WebServiceInfo("http://52.77.224.133:8089/ws_user/fetch_merchant_last_location");

            wsInfo.addParam("client_id",user.getClientId());
            wsInfo.addParam("session_id",user.getSessionId());
            mModel.sendRequestWithProgressbar(mView.getContext(), wsInfo, 2);
        } catch (Exception e) {
        }
    }

    public void processResponse(Response response, int type) {
  System.out.println("Resposne:"+response.getResponse());
        try {
            if (response.getStatus() == Status.SUCCESS) {
                JSONObject jo = new JSONObject(response.getResponse());
                if (jo.getString(JSONFlag.STATUS).equals(JSONFlag.SUCCESSFUL)) {
                    JSONArray array = jo.getJSONArray("result_data");
                    for (int i=0; i<array.length();i++){
                        MerchantLocationData ob = new MerchantLocationData();
                       JSONObject jOb  = array.getJSONObject(i);
                        ob.DISPLAYNAME = jOb.getString("display_name");
                        ob.CLIENTID = jOb.getString("client_id");
                        ob.LONGITUDE = jOb.getDouble("longitude");
                        ob.LATITUTE = jOb.getDouble("latitude");
                        ob.COUNTRY = jOb.getString("country");
                        ob.COUNTRYDESC = jOb.getString("country_desc");
                        ob.STATEDESC = jOb.getString("state_desc");
                        JSONObject con = jOb.getJSONObject("contact_details");
                        System.out.println("CON LEN:"+con.length());
                        if(con.length()>0){
                            ob.MOBILE = con.getString("mobile");
                            ob.RESIDENTIAL_TELNO = con.getString("residential_telephone");
                            ob.OFFICE_TELNO = con.getString("office_telephone");
                            ob.EMAIL = con.getString("email");
                        }

                        merchantData.add(ob);
                    }

                } else {
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
}
