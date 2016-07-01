package bluetooth.em.com.projectcountry.activity.remittance;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import UnlitechDevFramework.src.ud.framework.data.Response;
import UnlitechDevFramework.src.ud.framework.utilities.ViewUtil;
import bluetooth.em.com.projectcountry.R;
import bluetooth.em.com.projectcountry.controller.PeraPadalaPayoutController;
import bluetooth.em.com.projectcountry.data.ClientObjects;
import bluetooth.em.com.projectcountry.data.Title;
import bluetooth.em.com.projectcountry.model.PeraPadalaModel;
import bluetooth.em.com.projectcountry.view.PeraPadalaInterface;

/**
 * Created by Em on 7/1/2016.
 */
public class PeraPadalaPayout extends Fragment implements PeraPadalaInterface, PeraPadalaModel.PeraPadalaModelObserver {
    View view,sender,bene;
    private EditText search_refno;
    private PeraPadalaPayoutController mController;
    private PeraPadalaModel mModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.remittance_perapadala_payout, container, false);
        search_refno = (EditText)view.findViewById(R.id.et_search);
//        sender_stub = (ViewStub)view.findViewById(R.id.sender_stub);
//        bene_stub = (ViewStub)view.findViewById(R.id.bene_stub);
//        sender_stub.setLayoutResource(R.layout.remittance_client_data_payout);
//        sender_view = sender_stub.inflate();
//        bene_stub.setLayoutResource(R.layout.remittance_client_data_payout);
//        bene_view = bene_stub.inflate();
           sender = view.findViewById(R.id.include_sender_data);
            bene = view.findViewById(R.id.include_bene_data);
        sender.setVisibility(View.GONE);
        bene.setVisibility(View.GONE);
        mModel = new PeraPadalaModel();
        mModel.registerObserver(this);
        mController = new PeraPadalaPayoutController(this, mModel);
        search_refno.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (search_refno.getRight() - search_refno.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        clearFields();
                       if(ViewUtil.isEmpty(search_refno)){
                           showError(Title.PERAPADALAPAYOUT,"Please input tracking number.",null);
                       }else {
                           mController.searchRefno(search_refno.getText().toString());
                       }return true;
                    }
                }
                return false;
            }
        });
        view.findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mController.submit();
            }
        });
    return view;
    }

    private void clearFields() {
        sender.setVisibility(View.GONE);
        bene.setVisibility(View.GONE);
        view.findViewById(R.id.layout_details).setVisibility(View.GONE);
        view.findViewById(R.id.btn_submit).setVisibility(View.GONE);
        PeraPadalaInterface.PeraPadalaHolder holder = getCredentials(2);
        holder.control_no.setText("");
       }

    @Override
    public PeraPadalaHolder getCredentials(int type) {
        PeraPadalaHolder holder = new PeraPadalaHolder();
        if(type ==1){
            holder.sender_id = (TextView)sender.findViewById(R.id.tv_loyaltyno);
            holder.sender_fullname = (TextView)sender.findViewById(R.id.tv_name);
            holder.sender_bdate = (TextView)sender.findViewById(R.id.tv_bdate);
            holder.sender_mobile = (TextView)sender.findViewById(R.id.tv_mobile);
            holder.sender_email = (TextView)sender.findViewById(R.id.tv_email);

            holder.bene_id = (TextView)bene.findViewById(R.id.tv_loyaltyno);
            holder.bene_fullname = (TextView)bene.findViewById(R.id.tv_name);
            holder.bene_bdate = (TextView)bene.findViewById(R.id.tv_bdate);
            holder.bene_mobile = (TextView)bene.findViewById(R.id.tv_mobile);
            holder.bene_email = (TextView)bene.findViewById(R.id.tv_email);
        }else{
            holder.control_no = (TextView)view.findViewById(R.id.tv_ctrlno);
            holder.tv_amount = (TextView)view.findViewById(R.id.tv_amount);
            holder.charge = (TextView)view.findViewById(R.id.tv_charge);
            holder.date = (TextView)view.findViewById(R.id.tv_date);
            holder.origin = (TextView)view.findViewById(R.id.tv_origin);
            holder.idtype = (Spinner)view.findViewById(R.id.sp_idtype);
            holder.idno = (EditText)view.findViewById(R.id.et_idno);
            holder.tl_idno = (TextInputLayout)view.findViewById(R.id.tl_idno);
            holder.l_idtype = (TextView)view.findViewById(R.id.tv_l_idtype);
        }
        return holder;
    }

    @Override
    public void showRegistrationDialog(String searchtype) {}

    @Override
    public void showSearchResultDialo(ArrayList<ClientObjects> searchData, String type) {}

    @Override
    public void displaySearchResult(ClientObjects searchData) {
        sender.setVisibility(View.VISIBLE);
        bene.setVisibility(View.VISIBLE);
        view.findViewById(R.id.layout_details).setVisibility(View.VISIBLE);
        view.findViewById(R.id.btn_submit).setVisibility(View.VISIBLE);
        TextView title = (TextView)bene.findViewById(R.id.tv_title);
        title.setText("Beneficiary");
        PeraPadalaInterface.PeraPadalaHolder holder = getCredentials(1);
        PeraPadalaInterface.PeraPadalaHolder holder2 = getCredentials(2);
        holder.sender_id.setText(searchData.CARDNO);
        holder.sender_fullname.setText(searchData.FNAME + " " + searchData.MNAME + " " + searchData.LNAME);
        holder.sender_bdate.setText(searchData.BDATE);
        holder.sender_mobile.setText(searchData.MOBILE);
        holder.sender_email.setText(searchData.EMAIL);

        holder.bene_id.setText(searchData.BENE_CARDNO);
        holder.bene_fullname.setText(searchData.BENE_FNAME +" "+searchData.BENE_MNAME +" "+searchData.BENE_LNAME);
        holder.bene_bdate.setText(searchData.BENE_BDATE);
        holder.bene_mobile.setText(searchData.BENE_MOBILE);
        holder.bene_email.setText(searchData.BENE_EMAIL);

        holder2.control_no.setText(searchData.CONTROL_NO);
        holder2.tv_amount.setText(searchData.AMOUNT);
        holder2.charge.setText(searchData.CHARGE);
        holder2.date.setText(searchData.DATE);
        holder2.origin.setText(searchData.ORIGIN);

        mController.requestIdTypes();
    }

    @Override
    public void showError(String title, String message, DialogInterface.OnDismissListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.AppCompatAlertDialogStyle);
        builder.setTitle(Title.PERAPADALAPAYOUT);
        builder.setMessage(message);
        builder.setPositiveButton("OK", null);
        builder.setOnDismissListener(listener);
        builder.show();
    }

    @Override
    public void showRetryError(String title, String message, DialogInterface.OnDismissListener listener) {}

    @Override
    public void secondResponseReceived(Response response, int type) {
        mController.processResponse(response, type);
    }

    @Override
    public void errorOnRequest(Exception exception) {

    }

    @Override
    public void responseReceived(Response response, int type) {

    }
}
