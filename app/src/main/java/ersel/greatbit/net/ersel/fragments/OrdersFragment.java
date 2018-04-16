package ersel.greatbit.net.ersel.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import ersel.greatbit.net.ersel.R;
import ersel.greatbit.net.ersel.firebase.FCMRegistrationService;
import ersel.greatbit.net.ersel.http.HttpService;
import ersel.greatbit.net.ersel.http.IHttpService;
import ersel.greatbit.net.ersel.location.LocationUpdateService;
import ersel.greatbit.net.ersel.location.MyService;
import ersel.greatbit.net.ersel.models.GetShipments;
import ersel.greatbit.net.ersel.models.Shipment;
import ersel.greatbit.net.ersel.utilities.ConnectionDetector;
import ersel.greatbit.net.ersel.utilities.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OrdersFragment extends Fragment {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.orders_tab)
    TabLayout ordersTab;
//    @BindView(R.id.fragment_container)
//    LinearLayout fragmentContainer;
    @BindView(R.id.main_tab_content)
    ViewPager viewPager;

    Unbinder unbinder;



    @BindView(R.id.logout_user)
    ImageView logoutUser;
    private IHttpService iHttpService;



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
        Log.w("token",SharedPrefManager.getInstance(getActivity()).getToken());
        unbinder = ButterKnife.bind(this, view);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        iHttpService = HttpService.createService(IHttpService.class, SharedPrefManager.getInstance(getActivity()).getToken());
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupViewPager(viewPager);
        ordersTab.setupWithViewPager(viewPager);
        ordersTab.getTabAt(0).setText("جاري التنفيذ").setIcon(R.drawable.processing_tint);
        ordersTab.getTabAt(1).setText("تم الإستلام").setIcon(R.drawable.delivered_tint);
        ordersTab.getTabAt(2).setText("تم الرفض").setIcon(R.drawable.rejected_tint);
        ordersTab.getTabAt(3).setText("تحت الإنتظار").setIcon(R.drawable.on_hold_tint);
        ordersTab.getTabAt(0).select();
//        ordersTab.addTab(ordersTab.newTab().setText("جاري التنفيذ").setIcon(R.drawable.processing_tint));
//        ordersTab.addTab(ordersTab.newTab().setText("تم الإستلام").setIcon(R.drawable.delivered_tint));
//        ordersTab.addTab(ordersTab.newTab().setText("تم الرفض").setIcon(R.drawable.rejected_tint));
//        ordersTab.addTab(ordersTab.newTab().setText("تحت الإنتظار").setIcon(R.drawable.on_hold_tint));
//        // ordersTab.addTab(ordersTab.newTab().setText("تم التنفيذ").setIcon(R.drawable.completed));
//
//        ordersTab.getTabAt(0).select();
//        //replace default fragment
//        replaceFragment(ShipmentsFragment.newInstance(1));
//
//        //handling tab click event
//        ordersTab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                if (tab.getPosition() == 0) {
//                    replaceFragment(ShipmentsFragment.newInstance(1));
//                } else if (tab.getPosition() == 1) {
//                    replaceFragment(ShipmentsFragment.newInstance(3));
//                } else if (tab.getPosition() == 2) {
//                    replaceFragment(ShipmentsFragment.newInstance(4));
//                } else if (tab.getPosition() == 3) {
//                    replaceFragment(ShipmentsFragment.newInstance(5));
//                }
//
//
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.insertNewFragment(ShipmentsFragment.newInstance(1));
        adapter.insertNewFragment(ShipmentsFragment.newInstance(3));
        adapter.insertNewFragment(ShipmentsFragment.newInstance(4));
        adapter.insertNewFragment(ShipmentsFragment.newInstance(5));
       // viewPager.setOffscreenPageLimit(1);
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void insertNewFragment(Fragment fragment) {
            mFragmentList.add(fragment);
        }
    }



//    private void replaceFragment(Fragment fragment) {
//        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        transaction.replace(R.id.fragment_container, fragment);
//
//        transaction.commit();
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.logout_user})
    public void onViewClicked() {

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

                        final Intent intent = new Intent(getActivity(), MyService.class);
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

        final Intent intent = new Intent(getActivity(), MyService.class);
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



    }




}
