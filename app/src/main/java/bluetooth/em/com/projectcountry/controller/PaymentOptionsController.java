package bluetooth.em.com.projectcountry.controller;

import android.content.DialogInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import UnlitechDevFramework.src.ud.framework.data.Response;
import UnlitechDevFramework.src.ud.framework.data.enums.Status;
import UnlitechDevFramework.src.ud.framework.utilities.ViewUtil;
import UnlitechDevFramework.src.ud.framework.webservice.data.WebServiceInfo;
import bluetooth.em.com.projectcountry.data.ClientObjects;
import bluetooth.em.com.projectcountry.data.JSONFlag;
import bluetooth.em.com.projectcountry.data.Message;
import bluetooth.em.com.projectcountry.data.Title;
import bluetooth.em.com.projectcountry.data.User;
import bluetooth.em.com.projectcountry.model.PeraPadalaModel;
import bluetooth.em.com.projectcountry.model.UserModel;
import bluetooth.em.com.projectcountry.view.PaymentOptionsView;

/**
 * Created by Em on 6/28/2016.
 */
public class PaymentOptionsController {
    PaymentOptionsView mView;
    PeraPadalaModel mModel;
    public static ArrayList<ClientObjects> paymentmodes;
    public PaymentOptionsController(PaymentOptionsView view, PeraPadalaModel model) {
        paymentmodes = new ArrayList<>();
        mView=view;
        mModel=model;
    }

    public void requestPaymentOptions() {
        User user = new UserModel().getCurrentUser(mView.getContext());
        try {
            WebServiceInfo wsInfo = new WebServiceInfo("http://52.77.224.133:8089/ws_user/fetch_user_payment_types");
            wsInfo.addParam("client_id",user.getClientId());
            wsInfo.addParam("session_id", user.getSessionId());
            mModel.sendRequestWithProgressbar(mView.getContext(), wsInfo, 1);

        } catch (Exception e) {
            e.printStackTrace();
            mView.showError("LOGIN", Message.EXCEPTION, null);
        }   }

    public void processResponse(Response response, int type) {
        System.out.println("Response: " +response.getResponse());
        try {
            if (response.getStatus() == Status.SUCCESS) {
                JSONObject jo = new JSONObject(response.getResponse());
                if (jo.getString(JSONFlag.STATUS).equals(JSONFlag.SUCCESSFUL)) {
                    switch (type){
                        case 1:
                            JSONArray payments = jo.getJSONArray("result_data");
                            paymentmodes.clear();
                            for (int i = 0;i< payments.length();i++){
                                JSONObject ob = payments.getJSONObject(i);
                                ClientObjects client = new ClientObjects();
                                client.TYPEID = ob.getString("payment_type");
                                client.VALUE = ob.getString("value");
                                if(ob.has("expiry")){
                                    client.EXPIRY = ob.getString("expiry");
                                }
                                paymentmodes.add(client);
                            }
//                            mView.showError("", jo.getString(JSONFlag.MESSAGE), new DialogInterface.OnDismissListener() {
//                                @Override
//                                public void onDismiss(DialogInterface dialog) {
                                  mView.displayPayment(paymentmodes);
//                                }
//                            });

                            break;
                        case 2:
                            mView.showError("", jo.getString(JSONFlag.MESSAGE), new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog) {
                                    PaymentOptionsView.PeraPadalaHolder holder = mView.getCredentials(2);
                                    holder.mAlertDialog.dismiss();
                                   requestPaymentOptions();
                                }
                            });
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

    public void addPaymentMode() {
        User user = new UserModel().getCurrentUser(mView.getContext());
        try {
            PaymentOptionsView.PeraPadalaHolder holder = mView.getCredentials(2);
            WebServiceInfo wsInfo = new WebServiceInfo("http://52.77.224.133:8089/ws_user/credit_card_registration");
            wsInfo.addParam("client_id",user.getClientId());
            wsInfo.addParam("session_id", user.getSessionId());
            wsInfo.addParam("ccv", holder.ccv.getText().toString());
            wsInfo.addParam("cardno", holder.cardno.getText().toString());
            String month = String.valueOf(holder.ex_month.getSelectedItemPosition());
            if(month.length()==1){
                month = "0".concat(month);
            }
            wsInfo.addParam("month_expiry", month);
            String year1 = holder.ex_year.getSelectedItem().toString();
            String year2 = year1.substring(year1.length() -2);
            wsInfo.addParam("year_expiry", year2);
            System.out.println("YEAR:"+year2 +" MONTH:"+  month);
            mModel.sendRequestWithProgressbar(mView.getContext(), wsInfo, 2);

        } catch (Exception e) {
            e.printStackTrace();
            mView.showError("LOGIN", Message.EXCEPTION, null);
        }
    }

    public void submitRegistration() {
        PaymentOptionsView.PeraPadalaHolder holder = mView.getCredentials(2);
        holder.tl_cardno.setError(null);
        holder.tl_ccv.setError(null);
        if(validRegistrationCredentials()){
            addPaymentMode();
        }
    }

    private boolean validRegistrationCredentials() {
        boolean result = true;
        String messgae = "This field is required.";
        PaymentOptionsView.PeraPadalaHolder holder = mView.getCredentials(2);
        if(ViewUtil.isEmpty(holder.cardno)){
            holder.tl_cardno.setError(messgae);   result = false;
        }else if(ViewUtil.isEmpty(holder.ccv)){
            holder.tl_ccv.setError(messgae);   result = false;
        }else if(holder.ex_month.getSelectedItemPosition() ==0){
          mView.showError(Title.PERAPADALASEND,"Please choose month of expiration", null);   result = false;
        }else if(holder.ex_year.getSelectedItemPosition() ==0){
            mView.showError(Title.PERAPADALASEND,"Please choose year of expiration", null);   result = false;
        }
        return  result; }
}
