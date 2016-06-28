package bluetooth.em.com.projectcountry.data.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import bluetooth.em.com.projectcountry.R;
import bluetooth.em.com.projectcountry.data.ClientObjects;
import bluetooth.em.com.projectcountry.view.PaymentOptionsView;

/**
 * Created by Em on 6/28/2016.
 */
public class PadalaPaymentsAdapter extends RecyclerView.Adapter<PadalaPaymentsAdapter.ViewHolder> {
    private PaymentOptionsView mView;
    ArrayList<ClientObjects> objects;
    Context appcontext;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView name,bdate;
        ImageView icon;
        ViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            name = (TextView)itemView.findViewById(R.id.tv_name);
            bdate = (TextView)itemView.findViewById(R.id.tv_bdate);
            icon = (ImageView)itemView.findViewById(R.id.icon);

        }
    }
    public PadalaPaymentsAdapter(Context context, PaymentOptionsView view,ArrayList<ClientObjects> objects) {
        this.objects = objects;
        this.appcontext = context;
        mView=view;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.remittance_client_search_item, parent, false);
        ViewHolder pvh = new ViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.icon.setImageResource(R.drawable.ic_payment_method);
        holder.name.setText(Html.fromHtml("<b>TYPE : </b>" + objects.get(position).TYPEID ) );
        holder.bdate.setText(Html.fromHtml("<b>VALUE: </b>" + objects.get(position).VALUE) );
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(position);
            }
        });
    }

    private void showDialog(final int POSITION) {
        AlertDialog.Builder builder = new AlertDialog.Builder(appcontext,R.style.AppCompatAlertDialogStyle);
        builder.setMessage("Choose as payment method?");
        builder.setPositiveButton("CHOOSE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {System.out.println("CHOOSE:" + POSITION);
//            mView.displayPayment(POSITION);

                Intent returnIntent = new Intent();
                returnIntent.putExtra("id", objects.get(POSITION).TYPEID);
                returnIntent.putExtra("value", objects.get(POSITION).VALUE);
                mView.getActivity().setResult(1, returnIntent);
                mView.getActivity().finish();
            }
        });
        builder.setNegativeButton("CANCEL", null);
        builder.show();
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
