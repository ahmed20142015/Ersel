package ersel.greatbit.net.ersel.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ersel.greatbit.net.ersel.R;


public class ReceivedOrdersFragment extends Fragment {

    // TODO: Rename and change types and number of parameters
    public static ReceivedOrdersFragment newInstance(String param1, String param2) {
        ReceivedOrdersFragment fragment = new ReceivedOrdersFragment();
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
        return inflater.inflate(R.layout.fragment_received_orders, container, false);
    }

    


}
