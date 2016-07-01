package bluetooth.em.com.projectcountry.data.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import bluetooth.em.com.projectcountry.R;
import bluetooth.em.com.projectcountry.data.ClientObjects;
import bluetooth.em.com.projectcountry.view.PeraPadalaInterface;

/**
 * Created by Em on 6/17/2016.
 */
public class PadalaSearchAdapter extends RecyclerView.Adapter<PadalaSearchAdapter.ViewHolder> {
    ArrayList<ClientObjects> objects;
    Context appcontext;
    PeraPadalaInterface.PeraPadalaHolder parent_holder;
    AlertDialog searchResultDialog;
    String searchtype;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView name,bdate;
        ViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            name = (TextView)itemView.findViewById(R.id.tv_name);
            bdate = (TextView)itemView.findViewById(R.id.tv_bdate);
        }
    }
    public PadalaSearchAdapter(Context context, ArrayList<ClientObjects> objects, AlertDialog searchResultDialog, PeraPadalaInterface.PeraPadalaHolder credentials,String type) {
        this.objects = objects;
        this.appcontext = context;
        this.parent_holder = credentials;
        this.searchResultDialog =searchResultDialog;
        this.searchtype = type;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.remittance_client_search_item, parent, false);
        ViewHolder pvh = new ViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.name.setText(Html.fromHtml("<b>NAME : </b>" + objects.get(position).FNAME +" " +objects.get(position).MNAME +" "+objects.get(position).LNAME) );
        holder.bdate.setText(Html.fromHtml("<b>BIRTH DATE: </b>" + objects.get(position).BDATE) );
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(position);
            }
        });
    }

    private void showDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(appcontext,R.style.AppCompatAlertDialogStyle);
        LayoutInflater inflater = (LayoutInflater) appcontext.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View view = inflater.inflate(R.layout.remittance_client_search_item, null);
        builder.setView(view);
        TextView loyalty = (TextView)view.findViewById(R.id.tv_loyaltyno);
        TextView name = (TextView)view.findViewById(R.id.tv_name);
        TextView bday = (TextView)view.findViewById(R.id.tv_bdate);
        TextView email = (TextView)view.findViewById(R.id.tv_email);
        TextView mobile = (TextView)view.findViewById(R.id.tv_mobile);
        loyalty.setVisibility(View.VISIBLE);
        name.setVisibility(View.VISIBLE);
        bday.setVisibility(View.VISIBLE);
        email.setVisibility(View.VISIBLE);
        mobile.setVisibility(View.VISIBLE);
        loyalty.setText(Html.fromHtml("<b>ID NUMBER: </b>" + objects.get(position).CARDNO));
        name.setText(Html.fromHtml("<b>NAME : </b>" + objects.get(position).FNAME +" " +objects.get(position).MNAME +" "+objects.get(position).LNAME) );
        bday.setText(Html.fromHtml("<b>BIRTHDAY: </b>" + objects.get(position).BDATE));
        email.setText(Html.fromHtml("<b>EMAIL: </b>" + objects.get(position).EMAIL));
        mobile.setText(Html.fromHtml("<b>MOBILE: </b>" + objects.get(position).MOBILE));
        builder.setPositiveButton("SET AS "+searchtype, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showDetails(position);
                searchResultDialog.dismiss();
            }
        });
        builder.setNegativeButton("DISMISS", null);
        builder.show();
    }

    private void showDetails(int position) {
        LinearLayout sender_layuout = (LinearLayout)parent_holder.sender_data.findViewById(R.id.details);
        LinearLayout bene_layuout = (LinearLayout)parent_holder.bene_data.findViewById(R.id.details);
        if(searchtype.equals("SENDER")){
            sender_layuout.setVisibility(View.VISIBLE);
            TextView loyalty = (TextView)parent_holder.sender_data.findViewById(R.id.tv_loyaltyno);
            TextView fullname = (TextView)parent_holder.sender_data.findViewById(R.id.tv_name);
            TextView bdate = (TextView)parent_holder.sender_data.findViewById(R.id.tv_bdate);
            TextView mobile = (TextView)parent_holder.sender_data.findViewById(R.id.tv_mobile);
            TextView email = (TextView)parent_holder.sender_data.findViewById(R.id.tv_email);
            loyalty.setText(objects.get(position).CARDNO);
            fullname.setText(objects.get(position).FNAME +" " +objects.get(position).MNAME +" "+objects.get(position).LNAME);
            bdate.setText(objects.get(position).BDATE);
            email.setText( objects.get(position).EMAIL);
            mobile.setText(objects.get(position).MOBILE);
        }else if(searchtype.equals("BENEFICIARY")){
            parent_holder.payment_option.setVisibility(View.VISIBLE);
            bene_layuout.setVisibility(View.VISIBLE);
            TextView loyalty = (TextView)parent_holder.bene_data.findViewById(R.id.tv_loyaltyno);
            TextView fullname = (TextView)parent_holder.bene_data.findViewById(R.id.tv_name);
            TextView bdate = (TextView)parent_holder.bene_data.findViewById(R.id.tv_bdate);
            TextView mobile = (TextView)parent_holder.bene_data.findViewById(R.id.tv_mobile);
            TextView email = (TextView)parent_holder.bene_data.findViewById(R.id.tv_email);
            loyalty.setText(objects.get(position).CARDNO);
            fullname.setText(objects.get(position).FNAME +" " +objects.get(position).MNAME +" "+objects.get(position).LNAME);
            bdate.setText(objects.get(position).BDATE);
            email.setText( objects.get(position).EMAIL);
            mobile.setText(objects.get(position).MOBILE);
        }

    }

    @Override
    public int getItemCount() {
        return objects.size();
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}