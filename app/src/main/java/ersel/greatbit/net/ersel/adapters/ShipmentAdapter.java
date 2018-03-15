package ersel.greatbit.net.ersel.adapters;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
    private ArrayList<Shipment> shipments;

    View view;


    public ShipmentAdapter(Context context, ArrayList<Shipment> shipments) {
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
        holder.shipmentTrackNumber.setText(shipments.get(position).getTrackNumber());
        String dateTime = shipments.get(position).getDate();
        String[] parts = dateTime.split("T");
        holder.shipmentDate.setText(parts[0]);

        //Input date in String format
        String input = parts[1];
        //Date/time pattern of input date
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        //Date/time pattern of desired output date
        DateFormat outputformat = new SimpleDateFormat("hh:mm:ss aa");
        Date date = null;
        String output = null;
        try{
            //Conversion of input String to date
            date= df.parse(input);
            //old date format to new date format
            output = outputformat.format(date);
            holder.shipmentTime.setText(output);
        }catch(ParseException pe){
            pe.printStackTrace();
        }

        int type = shipments.get(position).getType();
        if(type == 0){
            holder.shipmentType.setText("Shipment");
        }
       else if(type == 1){
            holder.shipmentType.setText("Pickup");
        }

        holder.shipmentCity.setText(shipments.get(position).getAddressCityName());
        holder.shipmentAddress.setText(shipments.get(position).getAddressText());

    }

    @Override
    public int getItemCount() {

        return shipments.size();
    }


    public class myviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        @BindView(R.id.shipment_track_number)
        TextView shipmentTrackNumber;
        @BindView(R.id.shipment_date)
        TextView shipmentDate;
        @BindView(R.id.shipment_time)
        TextView shipmentTime;
        @BindView(R.id.shipment_type)
        TextView shipmentType;
        @BindView(R.id.shipment_city)
        TextView shipmentCity;
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