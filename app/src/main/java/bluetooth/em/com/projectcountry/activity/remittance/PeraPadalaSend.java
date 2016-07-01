package bluetooth.em.com.projectcountry.activity.remittance;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import UnlitechDevFramework.src.ud.framework.data.Response;
import bluetooth.em.com.projectcountry.R;
import bluetooth.em.com.projectcountry.controller.PeraPadalaSendController;
import bluetooth.em.com.projectcountry.data.ClientObjects;
import bluetooth.em.com.projectcountry.data.Title;
import bluetooth.em.com.projectcountry.data.adapters.PadalaSearchAdapter;
import bluetooth.em.com.projectcountry.model.PeraPadalaModel;
import bluetooth.em.com.projectcountry.view.PeraPadalaInterface;

/**
 * Created by Em on 6/16/2016.
 */
public class PeraPadalaSend extends Fragment implements PeraPadalaInterface, PeraPadalaModel.PeraPadalaModelObserver {
View view,sender_data,bene_data,payment_data;
    PeraPadalaModel mModel;
    PeraPadalaSendController mControler;
    private View dialogView;
    AlertDialog mAlertDialog,searchResultDialog;
    AlertDialog.Builder    builder;
    EditText sender_search,bene_search;
    private String payment_type_id="", payment_type_value="";
    ViewStub stub ;
    private TextView value,id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.remittance_perapadala_send, container, false);
        sender_data = view.findViewById(R.id.include_sender_data);
        bene_data = view.findViewById(R.id.include_bene_data);
//        stub = (ViewStub) view.findViewById(R.id.paymentdata_stub);
        TextView bene = (TextView)bene_data.findViewById(R.id.tv_title);
        bene.setText("Beneficiary");
         id =(TextView) view.findViewById(R.id.tv_id);
         value =(TextView) view.findViewById(R.id.tv_value);
        view.findViewById(R.id.tv_paymentOption).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PeraPadalaPaymentOptions.class);
                startActivityForResult(intent, 1);
            }
        });
        view.findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mControler.submit(payment_type_id,payment_type_value);
            }
        });
        mModel = new PeraPadalaModel();
        mModel.registerObserver(this);
        mControler =  new PeraPadalaSendController(this,mModel);
        sender_search = (EditText)sender_data.findViewById(R.id.et_search);
        bene_search = (EditText)bene_data.findViewById(R.id.et_search);

        sender_search.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (sender_search.getRight() - sender_search.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        mControler.searchUser(2, sender_search.getText().toString(), "SENDER");
                        return true;
                    }
                }
                return false;
            }
        });
        bene_search.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (bene_search.getRight() - bene_search.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
//                        showRegistration();
                        mControler.searchUser(2, bene_search.getText().toString(),"BENEFICIARY");
                        return true;
                    }
                }
                return false;
            }
        });
        return view;
    }

    private void showRegistration() {
      builder = new AlertDialog.Builder(getActivity(), R.style.AppCompatAlertDialogStyleBlue);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        dialogView = inflater.inflate(R.layout.remitrtance_client_registration, null);
        builder.setView(dialogView);
        mControler.registerUser(1);

        builder.setPositiveButton("REGISTER", null);
        builder.setNegativeButton("CANCEL", null);
         mAlertDialog = builder.create();
        mAlertDialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialog) {

                Button b = mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        // TODO Do something
                  mControler.registerUser(1);
                    }
                });
            }
        });
        mAlertDialog.show();
//        builder.setOnShowListener();
//        builder.show();
//        dialogView = new Dialog(getActivity());
//        dialogView.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialogView.setContentView(R.layout.remitrtance_client_registration);
//        dialogView.show();
    }

    @Override
    public PeraPadalaHolder getCredentials(int type) {
        PeraPadalaHolder holder = new PeraPadalaHolder();
//        if(type ==1){
//            stub.setLayoutResource(R.layout.remittance_client_search_item);
//            payment_data = stub.inflate();
//            holder.payment_data = payment_data;
//            holder.payment_id =(TextView) payment_data.findViewById(R.id.tv_name);
//            holder.payment_value =(TextView) payment_data.findViewById(R.id.tv_bdate);
//        }
         if (type == 2){
            holder.firstname = (EditText) dialogView.findViewById(R.id.et_firstname);
        holder.middlename = (EditText) dialogView.findViewById(R.id.et_middlename);
        holder.lastname = (EditText) dialogView.findViewById(R.id.et_lastname);
        holder.bdate = (EditText) dialogView.findViewById(R.id.et_bday);
        holder.mobile = (EditText) dialogView.findViewById(R.id.et_mobile);
        holder.email = (EditText) dialogView.findViewById(R.id.et_email);

        holder.tl_firstname = (TextInputLayout) dialogView.findViewById(R.id.tl_firstname);
        holder.tl_middlename = (TextInputLayout) dialogView.findViewById(R.id.tl_middlename);
        holder.tl_lastname = (TextInputLayout) dialogView.findViewById(R.id.tl_lastname);
        holder.tl_bdate = (TextInputLayout) dialogView.findViewById(R.id.tl_bday);
        holder.tl_mobile = (TextInputLayout) dialogView.findViewById(R.id.tl_mobile);
        holder.tl_email = (TextInputLayout) dialogView.findViewById(R.id.tl_email);

      }else {
            holder.builder = mAlertDialog;
            holder.searchDialog = searchResultDialog;
            holder.sender_data = view.findViewById(R.id.include_sender_data);
            holder.bene_data = view.findViewById(R.id.include_bene_data);
            holder.payment_option = (TextView)view.findViewById(R.id.tv_paymentOption);
            holder.bene_layout = (CardView)bene_data.findViewById(R.id.cv);
            holder.sender_id = (TextView)holder.sender_data.findViewById(R.id.tv_loyaltyno);
            holder.bene_id = (TextView)holder.bene_data.findViewById(R.id.tv_loyaltyno);
             holder.tl_amount =(TextInputLayout)view.findViewById(R.id.tl_amount);
             holder.amount = (EditText)view.findViewById(R.id.et_amount);
             holder.payment_id =(TextView) view.findViewById(R.id.tv_id);
             holder.payment_value =(TextView) view.findViewById(R.id.tv_value);

        }
        return holder;
    }

    @Override
    public void showRegistrationDialog(String searchtype) {
        builder = new AlertDialog.Builder(getActivity(), R.style.AppCompatAlertDialogStyleBlue);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        dialogView = inflater.inflate(R.layout.remitrtance_client_registration, null);
        builder.setView(dialogView);
        builder.setPositiveButton("REGISTER", null);
        builder.setNegativeButton("CANCEL", null);
        mAlertDialog = builder.create();
        mAlertDialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialog) {

                Button b = mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        // TODO Do something
                        mControler.registerUser(1);
                    }
                });
            }
        });
        mAlertDialog.show();
    }

    @Override
    public void showSearchResultDialo(ArrayList<ClientObjects> searchData,String type) {
        AlertDialog.Builder  builder = new AlertDialog.Builder(getActivity(), R.style.AppCompatAlertDialogStyleBlue);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.remittance_client_result, null);
        builder.setView(view);
        RecyclerView recList = (RecyclerView)view. findViewById(R.id.cardList);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(view.getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        recList.setAdapter(null);
        builder.setNegativeButton("DISMISS", null);
        searchResultDialog = builder.create();
        PadalaSearchAdapter adaper = new PadalaSearchAdapter(getActivity(),searchData,searchResultDialog,getCredentials(3),type);
        recList.setAdapter(adaper);
        searchResultDialog.show();
    }

    @Override
    public void displaySearchResult(ClientObjects searchData) {

    }

    @Override
    public void showError(String title, String message, DialogInterface.OnDismissListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.AppCompatAlertDialogStyle);
        builder.setTitle(Title.PERAPADALASEND);
        builder.setMessage(message);
        builder.setPositiveButton("OK", null);
        builder.setOnDismissListener(listener);
        builder.show();
    }

    @Override
    public void showRetryError(String title, String message, DialogInterface.OnDismissListener listener) {

    }

    @Override
    public void secondResponseReceived(Response response, int type) {
        mControler.processResponse(response, type);

    }

    @Override
    public void errorOnRequest(Exception exception) {

    }

    @Override
    public void responseReceived(Response response, int type) {
        mControler.processResponse(response, type);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("hello tehre:"+resultCode + " " + requestCode );
        if (resultCode == 1) {
            if (resultCode != 0) {
                payment_type_id = data.getStringExtra("id");
                payment_type_value = data.getStringExtra("value");
                view.findViewById(R.id.cv).setVisibility(View.VISIBLE);
                id.setText("PAYMENT TYPE:"+payment_type_id);
                value.setText("PAYMENT ID:" +payment_type_value);
                System.out.println("ID:" + payment_type_id + " " + payment_type_value);
            }
        }

    }
}
