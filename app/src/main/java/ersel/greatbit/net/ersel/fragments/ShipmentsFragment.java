package ersel.greatbit.net.ersel.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
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
    Unbinder unbinder;
    @BindView(R.id.shipments_progress)
    ProgressBar shipmentsProgress;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout refreshLayout;

    private IHttpService iHttpService;
    int shipmentStatus;
    ShipmentAdapter adapter;
    ArrayList<Shipment> shipments = new ArrayList<>();

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

        if (ConnectionDetector.getInstance(getActivity()).isConnectingToInternet())
            getShipments();
        else
            Toast.makeText(getActivity(), "Please Check Internet Connection", Toast.LENGTH_SHORT).show();

        refreshLayout.setColorSchemeResources(
                R.color.colorPrimary,
                R.color.colorPrimaryDark);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getShipments();
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
                    if(shipmentsProgress != null)
                    shipmentsProgress.setVisibility(View.GONE);
                    refreshLayout.setRefreshing(false);
                    for (int i = 0; i < response.body().getShipments().size(); i++) {
                        shipments.add(response.body().getShipments().get(i));
                    }
                    if (shipments.size() > 0) {
                        shipmentsList.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }

                } else{
                    shipmentsProgress.setVisibility(View.GONE);
                    refreshLayout.setRefreshing(false);
                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                }



            }

            @Override
            public void onFailure(Call<GetShipments> call, Throwable t) {
                shipmentsProgress.setVisibility(View.GONE);
                refreshLayout.setRefreshing(false);
                Toast.makeText(getActivity(), "Faill", Toast.LENGTH_SHORT).show();
            }
        });

    }


}
