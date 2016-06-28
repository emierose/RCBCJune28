package bluetooth.em.com.projectcountry.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.dd.processbutton.iml.ActionProcessButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import UnlitechDevFramework.src.ud.framework.data.Response;
import UnlitechDevFramework.src.ud.framework.data.enums.Status;
import UnlitechDevFramework.src.ud.framework.utilities.ViewUtil;
import UnlitechDevFramework.src.ud.framework.webservice.data.WebServiceInfo;
import bluetooth.em.com.projectcountry.activity.MainActivity;
import bluetooth.em.com.projectcountry.R;
import bluetooth.em.com.projectcountry.data.BankObjects;
import bluetooth.em.com.projectcountry.data.BranchObjects;
import bluetooth.em.com.projectcountry.data.CountryObjects;
import bluetooth.em.com.projectcountry.data.JSONFlag;
import bluetooth.em.com.projectcountry.data.Message;
import bluetooth.em.com.projectcountry.data.Title;
import bluetooth.em.com.projectcountry.model.RegistrationModel;
import bluetooth.em.com.projectcountry.view.RegistrationHolder;
import bluetooth.em.com.projectcountry.view.RegistrationInterface;

/**
 * Created by Em on 5/11/2016.
 */
public class RegistrationController {
    RegistrationModel mModel;
    RegistrationInterface mView;
    public ArrayList<CountryObjects> countryObjects ;
    public ArrayList<CountryObjects> p_stateObjects ;
    public ArrayList<CountryObjects> s_stateObjects ;
    public ArrayList<CountryObjects> t_stateObjects ;
    public ArrayList<CountryObjects> bplace_stateObjects ;
    public ArrayList<BankObjects> bank_objects ;
    public static final int TYPE_P_COUNTRY = 2;
    public static final int TYPE_S_COUNTRY= 3;
    public static final int TYPE_T_COUNTRY= 4;
    public static final int TYPE_BPLACE_COUNTRY= 5;
    public static final int TYPE_BRANCH= 6;
    RegistrationHolder holder;
    public RegistrationController(RegistrationInterface view, RegistrationModel model) {
        mModel = model;
        mView = view;
        countryObjects = new ArrayList<CountryObjects>();
        p_stateObjects = new ArrayList<CountryObjects>();
        s_stateObjects = new ArrayList<CountryObjects>();
        t_stateObjects = new ArrayList<CountryObjects>();
        bplace_stateObjects = new ArrayList<CountryObjects>();
        bank_objects = new ArrayList<BankObjects>();
    }
    public void register() {
        //Checks if the credentials are valid then calls the loginToServer method
        if (validCredentials())
            submit();
    }

    private boolean validCredentials() {
        RegistrationHolder holder = mView.getregistrationCredentials();
        boolean result = true;
        String message = null;
        if (ViewUtil.isEmpty(holder.username)) {
           message = "Username is required.";
        }else if(ViewUtil.isEmpty(holder.tpass)) {
            message = "Password is required.";
        }else if(ViewUtil.isEmpty(holder.re_tpass)) {
            message = "Password is required.";
        }else if(!holder.tpass.getText().toString().equals(holder.re_tpass.getText().toString())) {
            message = "Password didn't match";
        }else if(ViewUtil.isEmpty(holder.firstname)) {
            message = "First Name is required.";
        }else if(ViewUtil.isEmpty(holder.lastname)) {
            message = "Last Name is required.";
        }else if(ViewUtil.isEmpty(holder.bdate)) {
            message = "Date of Birth is required.";
        }else if(holder.bday_country.getSelectedItemPosition() == 0) {
            message = "Please choose country of birthplace";
        }else if(holder.bday_state.getSelectedItemPosition() == 0) {
        message = "Please choose state of birthplace";
         }else if(holder.status.getSelectedItemPosition() == 0) {
            message = "Please choose status";
        }else if(holder.nationality.getSelectedItemPosition() == 0) {
            message = "Please choose nationality";
        } else if(ViewUtil.isEmpty(holder.mothername)) {
            message = "Mother's Maiden Name is required.";
        }else if(ViewUtil.isEmpty(holder.residential)) {
            message = "Residential contact number is required.";
        }else if(ViewUtil.isEmpty(holder.office)) {
            message = "Office contact number is required.";
        }else if(ViewUtil.isEmpty(holder.mobile)) {
            message = "Mobile number is required.";
        } else if(ViewUtil.isEmpty(holder.p_email)) {
            message = "Email is required.";
        }else if(holder.bank.getSelectedItemPosition() == 0) {
            message = "Please choose bank";
        }else if(holder.branch.getSelectedItemPosition() == 0) {
            message = "Please choose branch";
        }else if(ViewUtil.isEmpty(holder.tin)) {
            message = "Tin number is required.";
        }else if(ViewUtil.isEmpty(holder.sss)) {
            message = "SSS Number is required.";
        }else if(ViewUtil.isEmpty(holder.passport)) {
            message = "Passport Number is required.";
        }else if(ViewUtil.isEmpty(holder.cadrno)) {
            message = "Card Number is required.";
        }else if(ViewUtil.isEmpty(holder.acr)) {
            message = "Credit Card CCV Number is required.";
        }else if(ViewUtil.isEmpty(holder.p_add)) {
            message = "Primary Address is required.";
        }else if(holder.p_country.getSelectedItemPosition() == 0) {
            message = "Please choose country";
        }else if(holder.p_state.getSelectedItemPosition() == 0) {
            message = "Please choose state";
        }

        if(message != null){
            mView.showError(Title.REGISTRATION,message, null);
            result = false;
        }
        return result;
    }

    public void requestCountry() {
        try{
        WebServiceInfo wsInfo = new WebServiceInfo("http://52.77.224.133:8089/ws_user/fetch_country_list");
        mModel.sendRequest(mView.getContext(), wsInfo, 1);
        } catch (Exception e) {
            RegistrationHolder holder = mView.getregistrationCredentials();
            holder.tv_p_country.setCompoundDrawables(mView.getContext().getResources().getDrawable(R.drawable.ic_reload), null, null, null);
            holder.tv_p_country.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_reload, 0, 0, 0);
        }
    }
    public void requestBankBranches() {
        try{
            WebServiceInfo wsInfo = new WebServiceInfo("http://52.77.224.133:8089/ws_user/fetch_bank_branches");
            mModel.sendRequest(mView.getContext(), wsInfo, TYPE_BRANCH);
        } catch (Exception e) {
//            RegistrationHolder holder = mView.getregistrationCredentials();
//            holder.tv_p_country.setCompoundDrawables(mView.getContext().getResources().getDrawable(R.drawable.ic_reload),null,null,null);
//            holder.tv_p_country.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_reload,0,0,0);
//            System.out.println(e.getMessage());
        }
    }
    public void submit() {
        try {
        WebServiceInfo wsInfo = new WebServiceInfo("http://52.77.224.133:8089/ws_user/register_user");
        RegistrationHolder holder = mView.getregistrationCredentials();
            holder.submit.setMode(ActionProcessButton.Mode.ENDLESS);
            holder.submit.setProgress(1);
        String s_country_code="",t_country_code="",s_state="",t_state="";
        String p_country_code = countryObjects.get(holder.p_country.getSelectedItemPosition() - 1).COUNTRY_CODE;
        if(holder.s_country.getSelectedItemPosition()>0){
         s_country_code = countryObjects.get(holder.s_country.getSelectedItemPosition() - 1).COUNTRY_CODE;
            s_state = holder.s_state.getSelectedItem().toString();}
            else{
            s_state = "";
        }
        if(holder.t_country.getSelectedItemPosition()>0){
            t_country_code = countryObjects.get(holder.t_country.getSelectedItemPosition() - 1).COUNTRY_CODE;
            t_state = holder.s_state.getSelectedItem().toString();}
        else{
            t_state = "";

        }
        String bplace_country_code = countryObjects.get(holder.bday_country.getSelectedItemPosition() - 1).COUNTRY_CODE;
        String nationality = countryObjects.get(holder.nationality.getSelectedItemPosition() - 1).COUNTRY_CODE;
        String bank = bank_objects.get(holder.bank.getSelectedItemPosition() - 1).BANK_ID;
        String branch = bank_objects.get(holder.bank.getSelectedItemPosition() - 1).BRANCHES.get(holder.branch.getSelectedItemPosition()-1).BRANCH_ID;
        wsInfo.addParam("username", holder.username.getText().toString());
        wsInfo.addParam("password",holder.tpass.getText().toString());
        wsInfo.addParam("fname",holder.firstname.getText().toString());
        wsInfo.addParam("mname",holder.middlename.getText().toString());
        wsInfo.addParam("lname",holder.lastname.getText().toString());
        wsInfo.addParam("dob",holder.bdate.getText().toString());
        wsInfo.addParam("dob_cty",bplace_country_code);
        wsInfo.addParam("dob_state", holder.bday_state.getSelectedItem().toString());
        wsInfo.addParam("marital", holder.status.getSelectedItem().toString());
        wsInfo.addParam("mother_name",holder.mothername.getText().toString());
        wsInfo.addParam("nationality",nationality);
        wsInfo.addParam("tin",holder.tin.getText().toString());
        wsInfo.addParam("sss",holder.sss.getText().toString());
        wsInfo.addParam("passport",holder.passport.getText().toString());
        wsInfo.addParam("acr",holder.acr.getText().toString());
        wsInfo.addParam("card_no",holder.cadrno.getText().toString());
        wsInfo.addParam("ccv",holder.ccv.getText().toString());
        wsInfo.addParam("p_address",holder.p_add.getText().toString());
        wsInfo.addParam("p_country",p_country_code);
        wsInfo.addParam("p_state",holder.p_state.getSelectedItem().toString());
        wsInfo.addParam("s_address",holder.s_add.getText().toString());
        wsInfo.addParam("s_country",s_country_code);
        wsInfo.addParam("s_state",s_state);
        wsInfo.addParam("t_address",holder.t_add.getText().toString());
        wsInfo.addParam("t_country",t_country_code);
        wsInfo.addParam("t_state",t_state);
        wsInfo.addParam("residential",holder.residential.getText().toString());
        wsInfo.addParam("office",holder.office.getText().toString());
        wsInfo.addParam("mobile",holder.mobile.getText().toString());
        wsInfo.addParam("p_email",holder.p_email.getText().toString());
        wsInfo.addParam("s_email",holder.s_email.getText().toString());
        wsInfo.addParam("t_email",holder.t_email.getText().toString());
        wsInfo.addParam("b_code", bank);//bank
        wsInfo.addParam("b_branch",branch);

        //Tells the model to send the personal info
        mModel.registrationRequest(mView.getContext(), wsInfo, 0);
        } catch (Exception e) {
            e.printStackTrace();
            mView.showError("LOGIN", Message.EXCEPTION, null);
        }
    }
    public void processRegistrationResponse(Response response,int type) {
        System.out.println("RESPONSE:" + response.getResponse());
        holder = mView.getregistrationCredentials();
        try {
            if (response.getStatus() == Status.SUCCESS) {
                JSONObject jo = new JSONObject(response.getResponse());
                if (jo.getString(JSONFlag.STATUS).equals(JSONFlag.SUCCESSFUL)) {
                    switch (type) {
                        case 0:
                            holder.submit.setProgress(100);
                            AlertDialog.Builder builder = new AlertDialog.Builder(mView.getActivity(),R.style.AppCompatAlertDialogStyle);
                            builder.setTitle(Title.SUCCESSFUL_REGISTRATION);
                            builder.setMessage(jo.getString(JSONFlag.MESSAGE));
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(mView.getActivity(), MainActivity.class);
                                   mView. getActivity().startActivity(intent);
                                }
                            });

                            builder.show();
                            break;

                    }
                } else {
                    holder.submit.setProgress(0);
                    mView.showError(Title.REGISTRATION, jo.getString(JSONFlag.MESSAGE), null);
                }
            } else {
                holder.submit.setProgress(0);
                mView.showError(Title.REGISTRATION, Message.ERROR_FETCHING_DATA, null);
            }
        } catch (RuntimeException e) {
            holder.submit.setProgress(0);
            mView.showError("", Message.RUNTIME_ERROR, null);
            e.printStackTrace();
        } catch (JSONException e) {
            holder.submit.setProgress(0);
            mView.showError("", Message.JSON_ERROR, null);
            e.printStackTrace();
        }
    }
    public void processResponse(Response response,int type) {

        try {
            if (response.getStatus() == Status.SUCCESS) {
                JSONObject jo = new JSONObject(response.getResponse());
                if (jo.getString(JSONFlag.STATUS).equals(JSONFlag.SUCCESSFUL)) {
                    System.out.println("HI:" + type);
                    switch (type) {
                        case 0:
                            break;
                        case 1:
                                JSONArray array_data = jo.getJSONArray("result_data");
                                for (int i = 0; i < array_data.length(); i++) {
                                    JSONObject data = array_data.getJSONObject(i);
                                    CountryObjects ob = new CountryObjects();
                                    ob.COUNTRY_CODE = data.getString("country_code");
                                    ob.COUNTRY_DESC = data.getString("country_desc");
                                    countryObjects.add(ob);
                                }
                            loadCountryList();
                        break;
                        case TYPE_P_COUNTRY:
                            JSONArray array_data2 = jo.getJSONArray("result_data");
                            for (int i = 0; i < array_data2.length(); i++) {
                                JSONObject data = array_data2.getJSONObject(i);
                                CountryObjects ob = new CountryObjects();
                                System.out.println("STATE:" +  data.getString("state_code"));
                                ob.STATE_CODE = data.getString("state_code");
                                ob.STATE_DESC = data.getString("state_desc");
                                p_stateObjects.add(ob);

                            }
                            loadStateList(TYPE_P_COUNTRY);
                            break;
                        case TYPE_S_COUNTRY:
                            JSONArray array_data3 = jo.getJSONArray("result_data");
                            for (int i = 0; i < array_data3.length(); i++) {
                                JSONObject data = array_data3.getJSONObject(i);
                                CountryObjects ob = new CountryObjects();
                                ob.STATE_CODE = data.getString("state_code");
                                ob.STATE_DESC = data.getString("state_desc");
                                s_stateObjects.add(ob);
                            }
                            loadStateList(TYPE_S_COUNTRY);
                            break;
                        case TYPE_T_COUNTRY:
                            JSONArray array_data4 = jo.getJSONArray("result_data");
                            for (int i = 0; i < array_data4.length(); i++) {
                                JSONObject data = array_data4.getJSONObject(i);
                                CountryObjects ob = new CountryObjects();
                                ob.STATE_CODE = data.getString("state_code");
                                ob.STATE_DESC = data.getString("state_desc");
                               t_stateObjects.add(ob);
                            }
                            loadStateList(TYPE_T_COUNTRY);
                            break;
                        case TYPE_BPLACE_COUNTRY:
                            JSONArray array_data5 = jo.getJSONArray("result_data");
                            for (int i = 0; i < array_data5.length(); i++) {
                                JSONObject data = array_data5.getJSONObject(i);
                                CountryObjects ob = new CountryObjects();
                                ob.STATE_CODE = data.getString("state_code");
                                ob.STATE_DESC = data.getString("state_desc");
                                bplace_stateObjects.add(ob);
                            }
                            loadStateList(TYPE_BPLACE_COUNTRY);
                            break;
                        case TYPE_BRANCH:
                            System.out.println("RESPONSE6:" + response.getResponse());
                            JSONArray array_data7 = jo.getJSONArray("result_data");
                            for (int i = 0; i < array_data7.length(); i++) {
                                JSONObject data = array_data7.getJSONObject(i);
                                ArrayList<BranchObjects> BRANCHES = new ArrayList<BranchObjects>();
                                BankObjects ob = new BankObjects();
                                ob.BANK_ID = data.getString("Bank_id");
                                ob.BANK_NAME = data.getString("Bank_name");
                                JSONArray array_data8 = data.getJSONArray("Branches");
                                for (int j = 0; j < array_data8.length(); j++) {
                                    JSONObject data2 = array_data8.getJSONObject(j);
                                    BranchObjects ob2 = new BranchObjects();
                                    System.out.println("branch id:" + data2.getString("branch_id"))
                                    ;
                                    ob2.BRANCH_ID = data2.getString("branch_id");
                                    ob2.BRANCH_ADDRESS = data2.getString("branch_address");
                                    BRANCHES.add(ob2);
                                }
                                System.out.println("bank id:" + data.getString("Bank_id"));
                                ob.BRANCHES = BRANCHES;
                                bank_objects.add(ob);
                            }
                            loandBankList();
                            break;
                    }
                } else {
                    mView.showError(Title.REGISTRATION, jo.getString(JSONFlag.MESSAGE), null);
                }
            } else {
                mView.showError(Title.REGISTRATION, Message.ERROR_FETCHING_DATA, null);
            }
        } catch (RuntimeException e) {
            mView.showError("", Message.RUNTIME_ERROR, null);
            e.printStackTrace();
        } catch (JSONException e) {
            mView.showError("", Message.JSON_ERROR, null);
            e.printStackTrace();
        }
        System.out.println("RESPONSE:" + response.getResponse());


    }


    private void loadStateList(int typePCountry) {
        RegistrationHolder holder = mView.getregistrationCredentials();
        switch (typePCountry){
            case TYPE_P_COUNTRY:
                ArrayList<String> forSpinner = new ArrayList<String>();
                for (int i = 0; i < p_stateObjects.size(); i++) {
                    forSpinner.add(p_stateObjects.get(i).STATE_DESC);
                }
                forSpinner.add(0, "-- Select State --");
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(mView.getContext(), android.R.layout.simple_spinner_item, forSpinner);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                holder.p_state.setAdapter(adapter);
                break;
            case TYPE_S_COUNTRY:
                ArrayList<String> forSpinner2 = new ArrayList<String>();
                for (int i = 0; i < s_stateObjects.size(); i++) {
                    forSpinner2.add(s_stateObjects.get(i).STATE_DESC);
                }
                forSpinner2.add(0, "-- Select State --");
                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(mView.getContext(), android.R.layout.simple_spinner_item, forSpinner2);
                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                holder.s_state.setAdapter(adapter2);
                break;
            case TYPE_T_COUNTRY:
                ArrayList<String> forSpinner3 = new ArrayList<String>();
                for (int i = 0; i < t_stateObjects.size(); i++) {
                    forSpinner3.add(t_stateObjects.get(i).STATE_DESC);
                }
                forSpinner3.add(0, "-- Select State --");
                ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(mView.getContext(), android.R.layout.simple_spinner_item, forSpinner3);
                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                holder.t_state.setAdapter(adapter3);
                break;
            case TYPE_BPLACE_COUNTRY:
                ArrayList<String> forSpinner4 = new ArrayList<String>();
                for (int i = 0; i < bplace_stateObjects.size(); i++) {
                    forSpinner4.add(bplace_stateObjects.get(i).STATE_DESC);
                }
                forSpinner4.add(0, "-- Select State --");
                ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(mView.getContext(), android.R.layout.simple_spinner_item, forSpinner4);
                adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                holder.bday_state.setAdapter(adapter4);
                break;
        }
    }
    private void loandBankList() {
        RegistrationHolder holder = mView.getregistrationCredentials();
        holder.tv_p_country.clearAnimation();
        ArrayList<String> forSpinner = new ArrayList<String>();
        for (int i = 0; i < bank_objects.size(); i++) {
            forSpinner.add(bank_objects.get(i).BANK_NAME);
        }
        forSpinner.add(0, "-- Select Bank --");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mView.getContext(), android.R.layout.simple_spinner_item, forSpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.bank.setAdapter(adapter);
    }

    private void loadCountryList() {
            RegistrationHolder holder = mView.getregistrationCredentials();
            holder.tv_p_country.clearAnimation();
            holder.tv_p_state.clearAnimation();
            holder.tv_s_country.clearAnimation();
            holder.tv_s_state.clearAnimation();
            holder.tv_t_country.clearAnimation();
            holder.tv_t_state.clearAnimation();
            holder.tv_place_country.clearAnimation();
            holder.tv_place_state.clearAnimation();
            holder.text.clearAnimation();
            holder.tv_nationality.clearAnimation();
            ArrayList<String> forSpinner = new ArrayList<String>();
            for (int i = 0; i < countryObjects.size(); i++) {
                forSpinner.add(countryObjects.get(i).COUNTRY_DESC);
            }
            forSpinner.add(0, "-- Select Country --");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mView.getContext(), android.R.layout.simple_spinner_item, forSpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.p_country.setAdapter(adapter);
        holder.s_country.setAdapter(adapter);
        holder.t_country.setAdapter(adapter);
        holder.bday_country.setAdapter(adapter);
        holder.nationality.setAdapter(adapter);
    }

    public AdapterView.OnItemSelectedListener on_p_country_select() {
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0){
                searchState(position,TYPE_P_COUNTRY);}
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
    }
    public AdapterView.OnItemSelectedListener on_s_country_select() {
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0){
                    searchState(position,TYPE_S_COUNTRY);}
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
    }
    public AdapterView.OnItemSelectedListener on_t_country_select() {
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0){
                    searchState(position,TYPE_T_COUNTRY);}
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
    }
    public AdapterView.OnItemSelectedListener on_bplace_country_select() {
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0){
                    searchState(position,TYPE_BPLACE_COUNTRY);}
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
    }
    public AdapterView.OnItemSelectedListener on_bank_select() {
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<String> forSpinner4 = new ArrayList<String>();
                RegistrationHolder holder = mView.getregistrationCredentials();
                if(position >0) {

                    for (int i = 0; i < bank_objects.get(position-1).BRANCHES.size(); i++) {
                        forSpinner4.add( bank_objects.get(position-1).BRANCHES.get(i).BRANCH_ADDRESS);
                    }
                    forSpinner4.add(0, "-- Select Branch --");
                    ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(mView.getContext(), android.R.layout.simple_spinner_item, forSpinner4);
                    adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    holder.branch.setAdapter(adapter4);
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
    }
    private void searchState(int position, int type) {
        System.out.println("COUNTRY CODE :" + countryObjects.get(position-1).COUNTRY_CODE+"COUNTRY POS :" + position);
        WebServiceInfo wsInfo = new WebServiceInfo("http://52.77.224.133:8089/ws_user/fetch_state_list");
        String code =  countryObjects.get(position-1).COUNTRY_CODE;
        wsInfo.addParam("c_code",code);
        //Tells the model to send the personal info
        mModel.sendRequest(mView.getContext(), wsInfo, type);
    }


}
