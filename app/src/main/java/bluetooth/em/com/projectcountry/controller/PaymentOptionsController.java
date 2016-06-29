package bluetooth.em.com.projectcountry.controller;

import android.content.DialogInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import UnlitechDevFramework.src.ud.framework.data.Response;
import UnlitechDevFramework.src.ud.framework.data.enums.Status;
import UnlitechDevFramework.src.ud.framework.webservice.data.WebServiceInfo;
import bluetooth.em.com.projectcountry.data.ClientObjects;
import bluetooth.em.com.projectcountry.data.JSONFlag;
import bluetooth.em.com.projectcountry.data.Message;
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
                                paymentmodes.add(client);
                            }
                            mView.showError("", jo.getString(JSONFlag.MESSAGE), new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog) {
                                  mView.displayPayment(paymentmodes);
                                }
                            });

                            break;
                        case 2:
                            mView.showError("", jo.getString(JSONFlag.MESSAGE), new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog) {
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
            WebServiceInfo wsInfo = new WebServiceInfo("http://52.77.224.133:8089/ws_user/credit_card_registration");
            wsInfo.addParam("client_id",user.getClientId());
            wsInfo.addParam("session_id", user.getSessionId());
            wsInfo.addParam("ccv", user.getSessionId());
            wsInfo.addParam("ccv", user.getSessionId());
            wsInfo.addParam("month_expiry", user.getSessionId());
            wsInfo.addParam("year_expiry", user.getSessionId());
            mModel.sendRequestWithProgressbar(mView.getContext(), wsInfo, 2);

        } catch (Exception e) {
            e.printStackTrace();
            mView.showError("LOGIN", Message.EXCEPTION, null);
        }
    }

    public void submitRegistration() {
    }
}
