package ersel.greatbit.net.ersel.adapters;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import ersel.greatbit.net.ersel.R;
import ersel.greatbit.net.ersel.fragments.OrderDetailsFragment;
import ersel.greatbit.net.ersel.models.Shipment;

/**
 * Created by Eslam on 3/11/2018.
 */

public class ShipmentAdapter extends RecyclerView.Adapter<ShipmentAdapter.myviewHolder> {


    private Context context;
    private ArrayList<Shipment> shipments ;

    View view;


    public ShipmentAdapter(Context context,ArrayList<Shipment> shipments ) {
        this.shipments = shipments;
        this.context = context;

    }


    @Override
    public myviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shipment_item, parent, false);

        return new myviewHolder(view);
    }


    @Override
    public void onBindViewHolder(myviewHolder holder, int position) {
        holder.clientName.setText(shipments.get(position).getClientName());
        holder.clientEmail.setText(shipments.get(position).getClientEmail());
        holder.recipientName.setText(shipments.get(position).getRecipientName());
        holder.recipientPhoneNumber.setText(shipments.get(position).getMobile());
        holder.shipmentAddress.setText(shipments.get(position).getAddressAreaName());
    }

    @Override
    public int getItemCount() {

        return shipments.size();
    }


    public class myviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.client_name)
        TextView clientName;
        @BindView(R.id.client_email)
        TextView clientEmail;
        @BindView(R.id.recipient_name)
        TextView recipientName;
        @BindView(R.id.recipient_phone_number)
        TextView recipientPhoneNumber;
        @BindView(R.id.shipment_address)
        TextView shipmentAddress;

        public myviewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
           int position = getAdapterPosition();
           int shipmentId = shipments.get(position).getId();
         //  Shipment shipment = shipments.get(position);
            ((FragmentActivity) context).getSupportFragmentManager().beginTransaction()
                    .add(R.id.mainContent,
                            OrderDetailsFragment.newInstance(shipmentId)).addToBackStack("OrderDetailsFragment").commit();

        }

    }
}