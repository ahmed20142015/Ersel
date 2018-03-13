package ersel.greatbit.net.ersel.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ersel.greatbit.net.ersel.R;


public class OrdersFragment extends Fragment {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.orders_tab)
    TabLayout ordersTab;
    @BindView(R.id.fragment_container)
    FrameLayout fragmentContainer;
    Unbinder unbinder;

    public static OrdersFragment newInstance(String param1, String param2) {
        OrdersFragment fragment = new OrdersFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_orders, container, false);

        unbinder = ButterKnife.bind(this, view);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ordersTab.addTab(ordersTab.newTab().setText("تحت الإنتظار"));
        ordersTab.addTab(ordersTab.newTab().setText("تم الرفض"));
        ordersTab.addTab(ordersTab.newTab().setText("تم الإستلام"));
        ordersTab.addTab(ordersTab.newTab().setText("جاي التنفيذ"));
        ordersTab.getTabAt(3).select();

        //replace default fragment
        replaceFragment(ShipmentsFragment.newInstance(1));

        //handling tab click event
        ordersTab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    replaceFragment(ShipmentsFragment.newInstance(5));
                } else if (tab.getPosition() == 1) {
                    replaceFragment(ShipmentsFragment.newInstance(4));
                }
                else if (tab.getPosition() == 2) {
                    replaceFragment(ShipmentsFragment.newInstance(3));
                }
                else if (tab.getPosition() == 3) {
                    replaceFragment(ShipmentsFragment.newInstance(1));
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
        android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);

        transaction.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
