package ersel.greatbit.net.ersel.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ersel.greatbit.net.ersel.R;
import ersel.greatbit.net.ersel.http.HttpService;
import ersel.greatbit.net.ersel.http.IHttpService;
import ersel.greatbit.net.ersel.models.BaseResponse;
import ersel.greatbit.net.ersel.models.Shipment;
import ersel.greatbit.net.ersel.models.ShipmentDetails;
import ersel.greatbit.net.ersel.utilities.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OrderDetailsFragment extends Fragment {
    //Computer SHA1 5A:3A:B9:E2:57:69:DA:3B:82:08:50:DF:AE:D1:EF:7C:22:53:91:BC
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.client_location)
    MapView mMapView;
    @BindView(R.id.details_client_name)
    TextView detailsClientName;
    @BindView(R.id.details_address)
    TextView detailsAddress;
    @BindView(R.id.details_first_number)
    TextView detailsFirstNumber;
    @BindView(R.id.details_second_number)
    TextView detailsSecondNumber;
    @BindView(R.id.shipment_number)
    TextView shipmentNumber;
    @BindView(R.id.shipment_cost)
    TextView shipmentCost;
    @BindView(R.id.shipment_note)
    TextView shipmentNote;
    @BindView(R.id.start_delivered_shipment)
    Button startDeliveredShipment;
    EditText rejectedReason;
    @BindView(R.id.shipment_under_wating)
    Button shipmentUnderWating;
    @BindView(R.id.shipment_rejected)
    Button shipmentRejected;
    @BindView(R.id.wait_cancle_layout)
    LinearLayout waitCancleLayout;
    private GoogleMap googleMap;
    Unbinder unbinder;
    int shipmentId;
    private IHttpService iHttpService;
    private Shipment shipment;
    String rejectReason = "";
    int newStatus;

    // TODO: Rename and change types and number of parameters
    public static OrderDetailsFragment newInstance(int shipmentId) {
        OrderDetailsFragment fragment = new OrderDetailsFragment();
        Bundle args = new Bundle();
        args.putInt("shipmentId", shipmentId);
        // args.putSerializable("shipmentId",shipment);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // shipment = (Shipment) getArguments().getSerializable("shipmentObject");
            shipmentId = getArguments().getInt("shipmentId");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        iHttpService = HttpService.createService(IHttpService.class, SharedPrefManager.getInstance(getActivity()).getToken());
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();

                Fragment frg = null;
                frg = getActivity().getSupportFragmentManager().findFragmentByTag("OrdersFragment");
                final FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.detach(frg);
                ft.attach(frg);
                ft.commit();
            }
        });

        getShipmentDetails();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initMap(final double lat, final double lng) {
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                // For dropping a marker at a point on the Map
                LatLng posisiabsen = new LatLng(lat, lng);
                googleMap.addMarker(new MarkerOptions().position(posisiabsen)
                        .title("client location"));
                CameraPosition cameraPosition = new CameraPosition.Builder().target(posisiabsen).zoom(17).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            }
        });

    }

    private void getShipmentDetails() {
        Call<ShipmentDetails> call = iHttpService.shipmentDetails(getArguments().getInt("shipmentId"));
        call.enqueue(new Callback<ShipmentDetails>() {
            @Override
            public void onResponse(Call<ShipmentDetails> call, Response<ShipmentDetails> response) {
                if (response.isSuccessful()) {
                    shipment = response.body().getShipment();
                    fillShipmentDetails();
                }
            }

            @Override
            public void onFailure(Call<ShipmentDetails> call, Throwable t) {

            }
        });

    }

    private void fillShipmentDetails() {
        if (shipment.getLastStatus() == 1){
            startDeliveredShipment.setVisibility(View.VISIBLE);
        }

        else if(shipment.getLastStatus()== 2){
            waitCancleLayout.setVisibility(View.VISIBLE);
        }



        // set client name
        detailsClientName.setText(shipment.getClientName());
        // set shipment address
        detailsAddress.setText(shipment.getRecipientAddressText());
        //set location on map
        double lat = shipment.getAddressLatitude();
        double lng = shipment.getAddressLongitude();
        initMap(lat, lng);
        //set phone numbers
        detailsFirstNumber.setText(shipment.getMobile());
        detailsSecondNumber.setText(shipment.getRecipientMobile());
        //set shipment number
        shipmentNumber.setText(shipment.getTrackNumber());
        //set shipment cost
        shipmentCost.setText(shipment.getCost() + "");
        //set shipment note
        shipmentNote.setText(shipment.getNotes());
    }


    @OnClick(R.id.start_delivered_shipment)
    public void onViewClicked() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.confirm_delivering_dialog, null);
        dialogBuilder.setView(dialogView);

        Button acceptDelivering = dialogView.findViewById(R.id.accept_delivering);
        Button cancleDelivering = dialogView.findViewById(R.id.cancle_delivering);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.setCancelable(true);
        alertDialog.show();

        acceptDelivering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newStatus = 2;
                alertDialog.dismiss();
                updateStatus();
            }
        });

        cancleDelivering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

    }

    private void rejectedReasonDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.rejected_reason_dialog, null);
        dialogBuilder.setView(dialogView);

        rejectedReason = dialogView.findViewById(R.id.rejected_reason);
        Button continueRejected = dialogView.findViewById(R.id.continue_reject);
        Button cancleRejected = dialogView.findViewById(R.id.cancle_reject);


        final AlertDialog reasonAlertDialog = dialogBuilder.create();
        reasonAlertDialog.setCancelable(true);
        reasonAlertDialog.show();

        cancleRejected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reasonAlertDialog.dismiss();
            }
        });

        continueRejected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reasonAlertDialog.dismiss();
                updateStatus();
            }
        });

    }

    private void updateStatus() {
        if(newStatus == 4 || newStatus == 5){
            rejectReason = rejectedReason.getText().toString();
        }

        Call<BaseResponse> call = iHttpService.updateStatus(getArguments().getInt("shipmentId"), shipment.getType(), newStatus, rejectReason);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful() && response.body().getStatusCode().equalsIgnoreCase("100")) {
                    Log.w("shipment", response.body().getStatus());
                    Toast.makeText(getActivity(), "تم تغيير حالة الشحنة", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getActivity(), "لا يمكن تغيير حالة الشحنة", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Faill", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick({R.id.shipment_under_wating, R.id.shipment_rejected})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.shipment_under_wating:
                newStatus = 5;
                rejectedReasonDialog();
                break;
            case R.id.shipment_rejected:
                newStatus = 4;
                rejectedReasonDialog();
                break;
        }
    }
}
