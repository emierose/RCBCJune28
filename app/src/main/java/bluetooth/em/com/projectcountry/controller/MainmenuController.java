package bluetooth.em.com.projectcountry.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import UnlitechDevFramework.src.ud.framework.data.Response;
import UnlitechDevFramework.src.ud.framework.data.enums.Status;
import UnlitechDevFramework.src.ud.framework.webservice.data.WebServiceInfo;
import bluetooth.em.com.projectcountry.R;
import bluetooth.em.com.projectcountry.activity.MapActivity;
import bluetooth.em.com.projectcountry.activity.MerchantLocationActivity;
import bluetooth.em.com.projectcountry.data.AccessData;
import bluetooth.em.com.projectcountry.data.Constants;
import bluetooth.em.com.projectcountry.data.JSONFlag;
import bluetooth.em.com.projectcountry.data.MerchantLocationData;
import bluetooth.em.com.projectcountry.data.Message;
import bluetooth.em.com.projectcountry.data.Title;
import bluetooth.em.com.projectcountry.data.User;
import bluetooth.em.com.projectcountry.model.AppGeneralModel;
import bluetooth.em.com.projectcountry.model.UserModel;
import bluetooth.em.com.projectcountry.view.MenuHolder;
import bluetooth.em.com.projectcountry.view.MenuInterface;

/**
 * Created by Em on 6/1/2016.
 */
public class MainmenuController {
    MenuInterface mView;
    AppGeneralModel mModel;
    ArrayList<AccessData> accessDataObjects;
 public static ArrayList<MerchantLocationData> merchantData;
    public MainmenuController(MenuInterface view, AppGeneralModel model) {
        mView=view;
        mModel=model;
        accessDataObjects = new ArrayList<>();
        merchantData = new ArrayList<>();
    }

    public void requestUserAccess() {
        try {
            WebServiceInfo wsInfo = new WebServiceInfo("http://52.77.224.133:8089/ws_user/get_user_access");
            User user= new UserModel().getCurrentUser(mView.getContext());
            wsInfo.addParam("client_id",user.getClientId());
            wsInfo.addParam("session_id",user.getSessionId());
            mModel.sendRequest(mView.getContext(), wsInfo, 1);
        } catch (Exception e) {
        }
    }

    public void requestLastLocation() {
        try {
            System.out.println("SENDING");
            User user= new UserModel().getCurrentUser(mView.getContext());
            WebServiceInfo wsInfo = new WebServiceInfo("http://52.77.224.133:8089/ws_user/fetch_merchant_last_location");
            wsInfo.addParam("client_id",user.getClientId());
            wsInfo.addParam("session_id",user.getSessionId());
            mModel.sendRequestWithProgressbar(mView.getContext(), wsInfo, 2);
        } catch (Exception e) {
        }
    }
    public void processResponse(Response response) {
        System.out.println("RESPONSE:"+response.getResponse());
        try {
            if (response.getStatus() == Status.SUCCESS) {
                JSONObject jo = new JSONObject(response.getResponse());
                if (jo.getString(JSONFlag.STATUS).equals(JSONFlag.SUCCESSFUL)) {
                    JSONArray array = jo.getJSONArray("result_data");
                    for (int i=0; i<array.length();i++){
                        ArrayList<String> modules = new ArrayList<>();
                        JSONArray array2 = array.getJSONArray(i);
                        AccessData ob = new AccessData();
                        ob.GROUP = i;
                        for (int j=0; j<array2.length();j++){
                            modules.add(array2.getString(j));
                        }
                        ob.MODULE_NAME = modules;
                        accessDataObjects.add(ob);
                    }
                    MenuHolder holder = mView.getrCredentials();
                    Menu m =holder. navigationView.getMenu();
                    for(int k = 0; k<accessDataObjects.size();k++){
                        for(int l = 0; l<accessDataObjects.get(k).MODULE_NAME.size();l++){
                            m.add(k, Menu.NONE, Menu.NONE, accessDataObjects.get(k).MODULE_NAME.get(l)).setIcon(R.drawable.ic_menu_send);
                        }
                    }
                    m.add(accessDataObjects.size(), Menu.NONE, Menu.NONE, "View Merchants Locations").setIcon(R.drawable.ic_menu_send);

                    System.out.println("ARRAY:" + accessDataObjects.get(1).MODULE_NAME.size());

                } else {
                    showRetryDialog(jo.getString(JSONFlag.MESSAGE));
                }
            } else {
                showRetryDialog(Message.ERROR_FETCHING_DATA);
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            showRetryDialog( Message.RUNTIME_ERROR);
        } catch (JSONException e) {
            e.printStackTrace();
            showRetryDialog(Message.JSON_ERROR);
        }
    }
    public void showRetryDialog(String messgae){
        mView.showRetryError(Title.REGISTRATION, messgae, new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                requestUserAccess();
            }
        });
    }
    public void processLocationResponse(Response response,int type) {
        System.out.println("LOCATION:"+response.getResponse());
        try {
            if (response.getStatus() == Status.SUCCESS) {
                JSONObject jo = new JSONObject(response.getResponse());

                if (jo.getString(JSONFlag.STATUS).equals(JSONFlag.SUCCESSFUL)) {
                    switch (type) {
                        case 2:
                            JSONObject jo2 = jo.getJSONObject("result_data");
                            if (jo2.getInt("isLocSet") == 1) {
                                Constants.LOCATION_STATUS = 1;
                                Constants.LONGITUDE = jo2.getDouble("longitude");
                                Constants.LATITUDE = jo2.getDouble("latitude");
                                Constants.LAST_UPDATE = jo2.getString("last_updated");
                                Intent intent = new Intent(mView.getActivity(), MapActivity.class);
                                mView.getActivity().startActivity(intent);
                            } else {
                                Constants.LOCATION_STATUS = 0;
                                Constants.LONGITUDE = 0.0;
                                Constants.LATITUDE = 0.0;
                                Constants.LAST_UPDATE = "";
                                Intent intent = new Intent(mView.getActivity(), MapActivity.class);
                                mView.getActivity().startActivity(intent);
                            }
                            break;
                        case 3:
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
                            Intent intent = new Intent(mView.getActivity(), MerchantLocationActivity.class);
                            mView.getActivity().startActivity(intent);
                            break;
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

    public void requestMerchantLocations() {
        try {
            User user= new UserModel().getCurrentUser(mView.getContext());
            WebServiceInfo wsInfo = new WebServiceInfo("http://52.77.224.133:8089/ws_user/fetch_all_merchant_location");
//            WebServiceInfo wsInfo = new WebServiceInfo("http://52.77.224.133:8089/ws_user/fetch_merchant_last_location");

            wsInfo.addParam("client_id",user.getClientId());
            wsInfo.addParam("session_id",user.getSessionId());
            mModel.sendRequestWithProgressbar(mView.getContext(), wsInfo, 3);
        } catch (Exception e) {
        }   }

}
