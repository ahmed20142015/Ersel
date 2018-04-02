package ersel.greatbit.net.ersel.fragments;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import ersel.greatbit.net.ersel.R;
import ersel.greatbit.net.ersel.activities.MainActivity;
import ersel.greatbit.net.ersel.firebase.FCMRegistrationService;
import ersel.greatbit.net.ersel.http.HttpService;
import ersel.greatbit.net.ersel.http.IHttpService;
import ersel.greatbit.net.ersel.location.LocationUpdateService;
import ersel.greatbit.net.ersel.models.GetShipments;
import ersel.greatbit.net.ersel.models.Shipment;
import ersel.greatbit.net.ersel.utilities.ConnectionDetector;
import ersel.greatbit.net.ersel.utilities.MyJobService;
import ersel.greatbit.net.ersel.utilities.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.JOB_SCHEDULER_SERVICE;


public class OrdersFragment extends Fragment {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.orders_tab)
    TabLayout ordersTab;
    @BindView(R.id.fragment_container)
    FrameLayout fragmentContainer;
    Unbinder unbinder;
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
    @BindView(R.id.logout_user)
    ImageView logoutUser;
    private IHttpService iHttpService;
    public  AlarmManager alarm;
    ArrayList<Shipment> deliveringShipmentItem = new ArrayList<>();

    public static OrdersFragment newInstance(String param1, String param2) {
        OrdersFragment fragment = new OrdersFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        startSevice();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_orders, container, false);

        unbinder = ButterKnife.bind(this, view);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        iHttpService = HttpService.createService(IHttpService.class, SharedPrefManager.getInstance(getActivity()).getToken());
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (ConnectionDetector.getInstance(getActivity()).isConnectingToInternet())
            getDeliveringShipments();
        else
            Toast.makeText(getActivity(), "Please Check Internet Connection", Toast.LENGTH_SHORT).show();


        ordersTab.addTab(ordersTab.newTab().setText("جاري التنفيذ").setIcon(R.drawable.processing_tint));
        ordersTab.addTab(ordersTab.newTab().setText("تم الإستلام").setIcon(R.drawable.delivered_tint));
        ordersTab.addTab(ordersTab.newTab().setText("تم الرفض").setIcon(R.drawable.rejected_tint));
        ordersTab.addTab(ordersTab.newTab().setText("تحت الإنتظار").setIcon(R.drawable.on_hold_tint));
        // ordersTab.addTab(ordersTab.newTab().setText("تم التنفيذ").setIcon(R.drawable.completed));

        ordersTab.getTabAt(0).select();
        //replace default fragment
        replaceFragment(ShipmentsFragment.newInstance(1));

        //handling tab click event
        ordersTab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    replaceFragment(ShipmentsFragment.newInstance(1));
                } else if (tab.getPosition() == 1) {
                    replaceFragment(ShipmentsFragment.newInstance(3));
                } else if (tab.getPosition() == 2) {
                    replaceFragment(ShipmentsFragment.newInstance(4));
                } else if (tab.getPosition() == 3) {
                    replaceFragment(ShipmentsFragment.newInstance(5));
                }


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }


    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);

        transaction.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.card_shipment_details,R.id.logout_user})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.card_shipment_details:
                   getActivity().getSupportFragmentManager().beginTransaction()
                          .add(R.id.mainContent,
                           OrderDetailsFragment.newInstance(deliveringShipmentItem.get(0).getId(), deliveringShipmentItem.get(0).getType()))
                           .addToBackStack("OrderDetailsFragment").commit();
                           break;

            case R.id.logout_user:
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                TextView textView = new TextView(getActivity());
                textView.setText("تسجيل الخروج");
                textView.setTextSize(20);
                textView.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                textView.setGravity(Gravity.CENTER);
                alert.setCustomTitle(textView);
                alert.setMessage("هل تريد تسجيل الخروج من إرســل ؟");
                alert.setPositiveButton("تسجيل الخروج", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPrefManager.getInstance(getActivity()).setToken(null);
                        dialog.dismiss();
                      //  stop sevice
//                        Intent intent = new Intent(getActivity(), LocationUpdateService.class);
//                        PendingIntent pintent = PendingIntent.getService(getActivity(), 0, intent, 0);
//                        getActivity().stopService(intent);
//                        alarm.cancel(pintent);

                        final Intent intent = new Intent(getActivity(), LocationUpdateService.class);
                        getActivity().stopService(intent);
                        getActivity().finish();
                    }
                });

                alert.setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                alert.show();
                break;
    }

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
                        cardShipmentDetails.setVisibility(View.VISIBLE);
                        deliveringShipments.setText("جاري تسليم شحنة :");
                        cardShipmentClientName.setText(deliveringShipmentItem.get(0).getClientName());
                        cardShipmentAddress.setText(deliveringShipmentItem.get(0).getAddressText());
                    } else {
                        deliveringShipments.setText("لا يوجد شحنات جاري تسليمها ...");
                        greenLight.setImageResource(R.color.red);
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


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startSevice(){

        //setNotificationToken
        if (!SharedPrefManager.getInstance(getActivity()).getSendToken()) {
            Intent tokenIntent = new Intent(getActivity(), FCMRegistrationService.class);
            Integer api = Integer.valueOf(Build.VERSION.SDK);
            if (api > 25)
                getActivity().startForegroundService(tokenIntent);
            else
                getActivity().startService(tokenIntent);
        }

        //   Init location service

        final Intent intent = new Intent(getActivity(), LocationUpdateService.class);
        Integer api = Integer.valueOf(Build.VERSION.SDK);
        if(api > 25)
        getActivity().startForegroundService(intent);
        else
            getActivity().startService(intent);


//        PendingIntent pintent = PendingIntent
//                .getService(getActivity(), 0, intent, 0);
//
//        alarm = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
//        // Start service every hour
//        alarm.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
       //         10000, pintent);
//
//        final Handler handler = new Handler();
//        Runnable runnable = new Runnable() {
//            @RequiresApi(api = Build.VERSION_CODES.O)
//            @Override
//            public void run() {
//              //  Integer api = Integer.valueOf(Build.VERSION.SDK);
//                getActivity().startService(intent);
//                handler.postDelayed(this, 10000);
//            }
//        };
//        handler.postDelayed(runnable, 10000);



//        ComponentName componentName = new ComponentName(getActivity(), MyJobService.class);
//        JobInfo jobInfo =
//                new JobInfo.Builder(1, componentName).setPeriodic(1000).build();
//    /*
//     * setPeriodic(long intervalMillis)
//     * Specify that this job should recur with the provided interval,
//     * not more than once per period.
//     */
//        JobScheduler jobScheduler = (JobScheduler)getActivity().getSystemService(JOB_SCHEDULER_SERVICE);
//        int jobId = jobScheduler.schedule(jobInfo);
//        if(jobScheduler.schedule(jobInfo)>0){
//
//        }else{
//
//        }


    }


}
