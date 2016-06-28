package bluetooth.em.com.projectcountry.controller;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import UnlitechDevFramework.src.ud.framework.data.Response;
import UnlitechDevFramework.src.ud.framework.data.enums.Status;
import UnlitechDevFramework.src.ud.framework.utilities.ViewUtil;
import UnlitechDevFramework.src.ud.framework.webservice.data.WebServiceInfo;
import bluetooth.em.com.projectcountry.R;
import bluetooth.em.com.projectcountry.data.ClientObjects;
import bluetooth.em.com.projectcountry.data.JSONFlag;
import bluetooth.em.com.projectcountry.data.Message;
import bluetooth.em.com.projectcountry.data.Title;
import bluetooth.em.com.projectcountry.data.User;
import bluetooth.em.com.projectcountry.model.PeraPadalaModel;
import bluetooth.em.com.projectcountry.model.UserModel;
import bluetooth.em.com.projectcountry.view.PeraPadalaInterface;

/**
 * Created by Em on 6/16/2016.
 */
public class PeraPadalaSendController {
    PeraPadalaInterface mView;
    PeraPadalaModel mModel;
    ArrayList<ClientObjects> clientList;
 public static ArrayList<ClientObjects> paymentmodes;
    String SEARCHTYPE;
    public PeraPadalaSendController(PeraPadalaInterface view, PeraPadalaModel model) {
        clientList = new ArrayList<>();
        paymentmodes = new ArrayList<>();
        mView = view;
        mModel = model;
    }

    public void processResponse(Response response, int type) {
        System.out.println("Response: " +response.getResponse());
        try {
            if (response.getStatus() == Status.SUCCESS) {
                JSONObject jo = new JSONObject(response.getResponse());
                if (jo.getString(JSONFlag.STATUS).equals(JSONFlag.SUCCESSFUL)) {
                    switch (type){
                        case 1:
                            mView.showError("Registration", jo.getString(JSONFlag.MESSAGE),null);
                            JSONObject job = jo.getJSONObject("result_data");
                            ClientObjects clientdata = new ClientObjects();
                            clientdata.CARDNO = job.getString("LOYALTY_ACCTNO");
                            clientdata.FNAME = job.getString("FN");
                            clientdata.MNAME = job.getString("MN");
                            clientdata.LNAME = job.getString("LN");
                            clientdata.BDATE = job.getString("BDATE");
                            clientdata.MOBILE = job.getString("MB");
                            clientdata.EMAIL = job.getString("EA");
                            displayDetails(clientdata);
                            break;
                        case 2:
//                            mView.showError("Registration", jo.getString(JSONFlag.MESSAGE),null);
                            JSONArray searchResult = jo.getJSONArray("result_data");
                            if(searchResult.length() == 0){
                                showRegistrationOption(jo.getString(JSONFlag.MESSAGE));
                            }else{
                                clientList.clear();
                                for (int i = 0;i< searchResult.length();i++){
                                    JSONObject ob = searchResult.getJSONObject(i);
                                    ClientObjects client = new ClientObjects();
                                    client.CARDNO = ob.getString("LOYALTY_ACCTNO");
                                    client.FNAME = ob.getString("FN");
                                    client.MNAME = ob.getString("MN");
                                    client.LNAME = ob.getString("LN");
                                    client.BDATE = ob.getString("BDATE");
                                    client.MOBILE = ob.getString("MB");
                                    client.EMAIL = ob.getString("EA");
                                    clientList.add(client);
                                }
                                showSearchResult();
                            }
                            break;
                    }
                } else {
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

    private void displayDetails( ClientObjects object) {
        PeraPadalaInterface.PeraPadalaHolder holder = mView.getCredentials(1);
        holder.builder.dismiss();
        LinearLayout sender_layuout = (LinearLayout)holder.sender_data.findViewById(R.id.details);
        LinearLayout bene_layuout = (LinearLayout)holder.bene_data.findViewById(R.id.details);
        if(SEARCHTYPE.equals("SENDER")){
            sender_layuout.setVisibility(View.VISIBLE);
            TextView loyalty = (TextView)holder.sender_data.findViewById(R.id.tv_loyaltyno);
            TextView fullname = (TextView)holder.sender_data.findViewById(R.id.tv_name);
            TextView bdate = (TextView)holder.sender_data.findViewById(R.id.tv_bdate);
            TextView mobile = (TextView)holder.sender_data.findViewById(R.id.tv_mobile);
            TextView email = (TextView)holder.sender_data.findViewById(R.id.tv_email);
            loyalty.setText(object.CARDNO);
            fullname.setText(object.FNAME +" " +object.MNAME +" "+object.LNAME);
            bdate.setText(object.BDATE);
            email.setText( object.EMAIL);
            mobile.setText(object.MOBILE);

        }else if(SEARCHTYPE.equals("BENEFICIARY")){
            holder.payment_option.setVisibility(View.VISIBLE);
            bene_layuout.setVisibility(View.VISIBLE);
            TextView loyalty = (TextView)holder.bene_data.findViewById(R.id.tv_loyaltyno);
            TextView fullname = (TextView)holder.bene_data.findViewById(R.id.tv_name);
            TextView bdate = (TextView)holder.bene_data.findViewById(R.id.tv_bdate);
            TextView mobile = (TextView)holder.bene_data.findViewById(R.id.tv_mobile);
            TextView email = (TextView)holder.bene_data.findViewById(R.id.tv_email);
            loyalty.setText(object.CARDNO);
            fullname.setText(object.FNAME +" " +object.MNAME +" "+object.LNAME);
            bdate.setText(object.BDATE);
            email.setText( object.EMAIL);
            mobile.setText(object.MOBILE);
        }
    }

    private void showSearchResult() {
        mView.showSearchResultDialo(clientList,SEARCHTYPE);
    }

    private void showRegistrationOption(String messgae) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mView.getActivity(), R.style.AppCompatAlertDialogStyle);
        builder.setTitle(Title.PERAPADALASEND);
        builder.setMessage(messgae);
        builder.setPositiveButton("REGISTER NEW CLIENT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showRegistrationDialog();
            }
        });
        builder.setNegativeButton("DISMISS",null);
        builder.show();
    }

    private void showRegistrationDialog() {
        mView.showRegistrationDialog(SEARCHTYPE);
    }

    public void registerUser(int i) {
        switch (i){
            case 1:
                PeraPadalaInterface.PeraPadalaHolder holder = mView.getCredentials(2);
                holder.tl_firstname.setError(null);
                holder.tl_middlename.setError(null);
                holder.tl_lastname.setError(null);
                holder.tl_mobile.setError(null);
                holder.tl_bdate.setError(null);
                holder.tl_email.setError(null);
                if(validregistrationCredentials()){
                    registrationRequest(1);
                }
                break;
            case 2:

                break;
        }
    }

    private void registrationRequest(int i) {
        User user = new UserModel().getCurrentUser(mView.getContext());
        PeraPadalaInterface.PeraPadalaHolder holder = mView.getCredentials(2);
        try {
            WebServiceInfo wsInfo = new WebServiceInfo("http://52.77.224.133:8089/ws_user/user_remittance_register");
            wsInfo.addParam("client_id",user.getClientId());
            wsInfo.addParam("session_id", user.getSessionId());
            wsInfo.addParam("firstname",holder.firstname.getText().toString());
            wsInfo.addParam("middlename",holder.middlename.getText().toString());
            wsInfo.addParam("lastname",holder.lastname.getText().toString());
            wsInfo.addParam("birthdate",holder.bdate.getText().toString());
            wsInfo.addParam("mobile",holder.mobile.getText().toString());
            wsInfo.addParam("email",holder.email.getText().toString());
            mModel.sendRequestWithProgressbar(mView.getContext(), wsInfo, i);

        } catch (Exception e) {
            e.printStackTrace();
            mView.showError("LOGIN", Message.EXCEPTION, null);
        }
    }

    private boolean validregistrationCredentials() {
        boolean result = true;
        String messgae = "This field is required.";
        PeraPadalaInterface.PeraPadalaHolder holder = mView.getCredentials(2);
        if(ViewUtil.isEmpty(holder.firstname)){
            holder.tl_firstname.setError(messgae);   result = false;
        }else if(ViewUtil.isEmpty(holder.middlename)){
            holder.tl_middlename.setError(messgae);   result = false;
        }else if(ViewUtil.isEmpty(holder.lastname)){
            holder.tl_lastname.setError(messgae);   result = false;
        }else if(ViewUtil.isEmpty(holder.bdate)){
            holder.tl_bdate.setError(messgae);   result = false;
        }else if(ViewUtil.isEmpty(holder.mobile)){
            holder.tl_mobile.setError(messgae);   result = false;
        }else if(ViewUtil.isEmpty(holder.email)){
            holder.tl_email.setError(messgae);   result = false;
        }
        return  result;
    }
    public void searchUser(int i, String key,String searchtype) {
        SEARCHTYPE = searchtype;
        User user = new UserModel().getCurrentUser(mView.getContext());
        try {
            WebServiceInfo wsInfo = new WebServiceInfo("http://52.77.224.133:8089/ws_user/user_remittance_search");
            wsInfo.addParam("client_id",user.getClientId());
            wsInfo.addParam("session_id", user.getSessionId());
            wsInfo.addParam("search_key",key);
            mModel.sendRequestWithProgressbar(mView.getContext(), wsInfo, i);

        } catch (Exception e) {
            e.printStackTrace();
            mView.showError("LOGIN", Message.EXCEPTION, null);
        }
    }

    public void submit() {
        if(validCredential()){
            
        }
    }

    private boolean validCredential() {
        return false;
    }
}
