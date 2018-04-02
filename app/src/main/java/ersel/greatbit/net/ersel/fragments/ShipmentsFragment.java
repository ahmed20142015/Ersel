package ersel.greatbit.net.ersel.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import ersel.greatbit.net.ersel.R;
import ersel.greatbit.net.ersel.adapters.ShipmentAdapter;
import ersel.greatbit.net.ersel.http.HttpService;
import ersel.greatbit.net.ersel.http.IHttpService;
import ersel.greatbit.net.ersel.models.GetShipments;
import ersel.greatbit.net.ersel.models.Shipment;
import ersel.greatbit.net.ersel.utilities.ConnectionDetector;
import ersel.greatbit.net.ersel.utilities.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ShipmentsFragment extends Fragment {

    @BindView(R.id.shipments_list)
    RecyclerView shipmentsList;

    @BindView(R.id.shipments_progress)
    ProgressBar shipmentsProgress;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout refreshLayout;


    @BindView(R.id.delivering_shipments)
    TextView deliveringShipments;
    @BindView(R.id.card_shipment_client_name)
    TextView cardShipmentClientName;
    @BindView(R.id.card_shipment_address)
    TextView cardShipmentAddress;
    @BindView(R.id.card_shipment_details)
    CardView cardShipmentDetails;
    @BindView(R.id.green_light)
    CircleImageView greenLight;
    Unbinder unbinder;

    private IHttpService iHttpService;
    int shipmentStatus;
    ShipmentAdapter adapter;
    ArrayList<Shipment> shipments = new ArrayList<>();
    ArrayList<Shipment> deliveringShipmentItem = new ArrayList<>();

    // TODO: Rename and change types and number of parameters
    public static ShipmentsFragment newInstance(int shipmentStatus) {
        ShipmentsFragment fragment = new ShipmentsFragment();
        Bundle args = new Bundle();
        args.putInt("shipmentStatus", shipmentStatus);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            shipmentStatus = getArguments().getInt("shipmentStatus");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shipments, container, false);
        unbinder = ButterKnife.bind(this, view);
        iHttpService = HttpService.createService(IHttpService.class, SharedPrefManager.getInstance(getActivity()).getToken());
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        shipmentsList.setLayoutManager(layoutManager);
        shipmentsList.setItemAnimator(new DefaultItemAnimator());
        adapter = new ShipmentAdapter(getActivity(), shipments);

        if (ConnectionDetector.getInstance(getActivity()).isConnectingToInternet()) {
            getShipments();
            getDeliveringShipments();
        } else
            Toast.makeText(getActivity(), "Please Check Internet Connection", Toast.LENGTH_SHORT).show();

        refreshLayout.setColorSchemeResources(
                R.color.colorPrimary,
                R.color.colorPrimaryDark);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                                               @Override
                                               public void onRefresh() {
                                                   getShipments();
                                                   getDeliveringShipments();
//                refreshLayout.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        refreshLayout.setRefreshing(false);
//                    }
//                }, 5000);

                                               }
                                           }
        );

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void getShipments() {
        shipmentsProgress.setVisibility(View.VISIBLE);
        Call<GetShipments> call = iHttpService.getShipments(getArguments().getInt("shipmentStatus"));
        call.enqueue(new Callback<GetShipments>() {
            @Override
            public void onResponse(Call<GetShipments> call, Response<GetShipments> response) {
                if (response.isSuccessful()) {
                    if (shipmentsProgress != null && refreshLayout != null){
                        shipmentsProgress.setVisibility(View.GONE);
                        refreshLayout.setRefreshing(false);
                    }

                    for (int i = 0; i < response.body().getShipments().size(); i++) {
                        shipments.add(response.body().getShipments().get(i));
                    }
                    if (shipments.size() > 0) {
                        shipmentsList.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }

                } else {
                    if (shipmentsProgress != null && refreshLayout != null){
                        shipmentsProgress.setVisibility(View.GONE);
                        refreshLayout.setRefreshing(false);
                    }
                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<GetShipments> call, Throwable t) {
                if (shipmentsProgress != null && refreshLayout != null){
                    shipmentsProgress.setVisibility(View.GONE);
                    refreshLayout.setRefreshing(false);
                }
                Toast.makeText(getActivity(), "Faill", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getDeliveringShipments() {
        Call<GetShipments> call = iHttpService.getShipments(2);
        call.enqueue(new Callback<GetShipments>() {
            @Override
            public void onResponse(Call<GetShipments> call, Response<GetShipments> response) {
                if (response.isSuccessful()) {
                    for (int i = 0; i < response.body().getShipments().size(); i++) {
                        deliveringShipmentItem.add(response.body().getShipments().get(i));
                    }
                    if (deliveringShipmentItem.size() > 0) {
                        if(cardShipmentDetails != null && deliveringShipments != null &&
                                cardShipmentClientName != null && cardShipmentAddress != null ){
                            cardShipmentDetails.setVisibility(View.VISIBLE);
                            deliveringShipments.setText("جاري تسليم شحنة :");
                            cardShipmentClientName.setText(deliveringShipmentItem.get(0).getClientName());
                            cardShipmentAddress.setText(deliveringShipmentItem.get(0).getAddressText());
                        }

                    } else {
                        if(deliveringShipments != null && greenLight != null){
                            deliveringShipments.setText("لا يوجد شحنات جاري تسليمها ...");
                            greenLight.setImageResource(R.color.red);
                        }

                    }

                } else
                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onFailure(Call<GetShipments> call, Throwable t) {
                Toast.makeText(getActivity(), "Faill", Toast.LENGTH_SHORT).show();
            }
        });

    }



    @OnClick(R.id.card_shipment_details)
    public void onViewClicked() {

        getActivity().getSupportFragmentManager().beginTransaction()
                .add(R.id.mainContent,
                        OrderDetailsFragment.newInstance(deliveringShipmentItem.get(0).getId(), deliveringShipmentItem.get(0).getType()))
                .addToBackStack("OrderDetailsFragment").commit();

    }
}
