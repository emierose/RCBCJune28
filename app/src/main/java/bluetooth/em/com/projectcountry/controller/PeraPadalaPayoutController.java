package bluetooth.em.com.projectcountry.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import UnlitechDevFramework.src.ud.framework.data.Response;
import UnlitechDevFramework.src.ud.framework.data.enums.Status;
import UnlitechDevFramework.src.ud.framework.utilities.ViewUtil;
import UnlitechDevFramework.src.ud.framework.webservice.data.WebServiceInfo;
import bluetooth.em.com.projectcountry.activity.MainActivity;
import bluetooth.em.com.projectcountry.data.ClientObjects;
import bluetooth.em.com.projectcountry.data.JSONFlag;
import bluetooth.em.com.projectcountry.data.Message;
import bluetooth.em.com.projectcountry.data.User;
import bluetooth.em.com.projectcountry.model.PeraPadalaModel;
import bluetooth.em.com.projectcountry.model.UserModel;
import bluetooth.em.com.projectcountry.view.PeraPadalaInterface;

/**
 * Created by Em on 7/1/2016.
 */
public class PeraPadalaPayoutController {
    PeraPadalaInterface mView ;
    PeraPadalaModel mModel;
    public ArrayList<ClientObjects> idTypeObjects ;
    private String CONTROLNO=" ";

    public PeraPadalaPayoutController(PeraPadalaInterface view, PeraPadalaModel model) {
        mView =view;
        mModel = model;
        idTypeObjects = new ArrayList<>();
    }

    public void searchRefno(String key) {
        User user = new UserModel().getCurrentUser(mView.getContext());
        try {
            WebServiceInfo wsInfo = new WebServiceInfo("http://52.77.224.133:8089/ws_user/search_remittance_transaction");
            wsInfo.addParam("client_id",user.getClientId());
            wsInfo.addParam("session_id", user.getSessionId());
            wsInfo.addParam("control_no",key);
            mModel.sendRequestWithProgressbar(mView.getContext(), wsInfo, 1);
        } catch (Exception e) {
            e.printStackTrace();
            mView.showError("", Message.EXCEPTION, null);
        }  }

    public void processResponse(Response response, int type) {
        System.out.println("Response: " + response.getResponse());
        try {
            if (response.getStatus() == Status.SUCCESS) {
                JSONObject jo = new JSONObject(response.getResponse());
                if (jo.getString(JSONFlag.STATUS).equals(JSONFlag.SUCCESSFUL)) {
                    switch (type){
                        case 1:
//                            mView.showError("Registration", jo.getString(JSONFlag.MESSAGE),null);
                            JSONObject job = jo.getJSONObject("result_data");
                            ClientObjects clientdata = new ClientObjects();
                            JSONObject jo1 = job.getJSONObject("Sender");
                            clientdata.CARDNO = jo1.getString("User_id");
                            clientdata.FNAME = jo1.getString("FirstName");
                            clientdata.MNAME = jo1.getString("MiddleName");
                            clientdata.LNAME = jo1.getString("LastName");
                            clientdata.BDATE = jo1.getString("Birthdate");
                            clientdata.MOBILE = jo1.getString("Mobile");
                            clientdata.EMAIL = jo1.getString("Email");

                            JSONObject jo2 = job.getJSONObject("Beneficiary");
                            clientdata.BENE_CARDNO = jo2.getString("User_id");
                            clientdata.BENE_FNAME = jo2.getString("FirstName");
                            clientdata.BENE_MNAME = jo2.getString("MiddleName");
                            clientdata.BENE_LNAME = jo2.getString("LastName");
                            clientdata.BENE_BDATE = jo2.getString("Birthdate");
                            clientdata.BENE_MOBILE = jo2.getString("Mobile");
                            clientdata.BENE_EMAIL = jo2.getString("Email");

                            clientdata.CONTROL_NO = job.getString("Control_no");
                            clientdata.AMOUNT = job.getString("Amount");
                            clientdata.CHARGE = job.getString("Charge");
                            clientdata.DATE = job.getString("Transaction_date");
                            clientdata.ORIGIN = job.getString("Transaction_origin");

                            CONTROLNO = job.getString("Control_no");
                             mView.displaySearchResult(clientdata);
                            break;
                        case 2:
                            idTypeObjects.clear();
                            JSONArray array_data = jo.getJSONArray("result_data");
                            for (int i = 0; i < array_data.length(); i++) {
                                JSONObject data = array_data.getJSONObject(i);
                                ClientObjects ob = new ClientObjects();
                                ob.IDTYPE = data.getString("id_type");
                                ob.IDDESC = data.getString("id_description");
                                idTypeObjects.add(ob);
                            }
                            loadIdList();
                            break;
                        case 3:
                            mView.showError("", jo.getString(JSONFlag.MESSAGE), new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog) {
                                    Intent intent = new Intent(mView.getActivity(), MainActivity.class);
                                    mView.getActivity().startActivity(intent);
                                }
                            });
                            break;
                    }
                } else {
                    if(type ==2){
                        PeraPadalaInterface.PeraPadalaHolder holder2 = mView.getCredentials(2);
                        holder2.l_idtype.setText("Reload Id List...");
                        holder2.l_idtype.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                requestIdTypes();
                            }
                        });
                    }else
                    mView.showError("", jo.getString(JSONFlag.MESSAGE), null);
                }
            } else {
                mView.showError("", Message.ERROR, null);
            }
        } catch (RuntimeException e) {
            mView.showError("", Message.RUNTIME_ERROR, null);
            e.printStackTrace();
        } catch (JSONException e) {
            mView.showError("", Message.JSON_ERROR, null);
        }
    }

    private void loadIdList() {
        PeraPadalaInterface.PeraPadalaHolder holder = mView.getCredentials(2);
        ArrayList<String> forSpinner = new ArrayList<String>();
        for (int i = 0; i < idTypeObjects.size(); i++) {
            forSpinner.add(idTypeObjects.get(i).IDDESC);
        }
        forSpinner.add(0, "-- Select State --");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mView.getContext(), android.R.layout.simple_spinner_item, forSpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.idtype.setAdapter(adapter);
    }

    public void submit() {
        PeraPadalaInterface.PeraPadalaHolder holder = mView.getCredentials(2);
        holder.tl_idno.setError(null);
        if(isValidCredentials()){
            sendRequest();
        }
    }

    private void sendRequest() {
        User user = new UserModel().getCurrentUser(mView.getContext());
        try {
            PeraPadalaInterface.PeraPadalaHolder holder = mView.getCredentials(2);
            WebServiceInfo wsInfo = new WebServiceInfo("http://52.77.224.133:8089/ws_user/payout_remittance_transaction");
            wsInfo.addParam("client_id",user.getClientId());
            wsInfo.addParam("session_id", user.getSessionId());
            wsInfo.addParam("control_no", CONTROLNO);
            wsInfo.addParam("id_type", idTypeObjects.get(holder.idtype.getSelectedItemPosition() -1).IDTYPE);
            wsInfo.addParam("id_no", holder.idno.getText().toString());

            mModel.sendRequestWithProgressbar(mView.getContext(), wsInfo, 3);
        } catch (Exception e) {
            e.printStackTrace();
            mView.showError("", Message.EXCEPTION, null);
        }
    }

    private boolean isValidCredentials() {
        PeraPadalaInterface.PeraPadalaHolder holder = mView.getCredentials(2);
        boolean result = true;
        if(holder.idtype.getSelectedItemPosition() == 0){
            mView.showError("","Please select ID type.",null); result =false;
        }else if(ViewUtil.isEmpty(holder.idno)){
            holder.tl_idno.setError("Please inout ID Number"); result = false;
        }
        return result;
    }

    public void requestIdTypes() {
        User user = new UserModel().getCurrentUser(mView.getContext());
        try {
            WebServiceInfo wsInfo = new WebServiceInfo("http://52.77.224.133:8089/ws_user/fetch_list_valid_ids");
            wsInfo.addParam("client_id",user.getClientId());
            wsInfo.addParam("session_id", user.getSessionId());
            mModel.sendRequestWithProgressbar(mView.getContext(), wsInfo, 2);
        } catch (Exception e) {
            e.printStackTrace();
            mView.showError("", Message.EXCEPTION, null);
        }
    }
}
