package ersel.greatbit.net.ersel.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ersel.greatbit.net.ersel.R;


public class ProcessingOrdersFragment extends Fragment {

    @BindView(R.id.processing_orders)
    TextView processingOrders;
    Unbinder unbinder;

    // TODO: Rename and change types and number of parameters
    public static ProcessingOrdersFragment newInstance(String param1, String param2) {
        ProcessingOrdersFragment fragment = new ProcessingOrdersFragment();
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
        View view = inflater.inflate(R.layout.fragment_processing, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.processing_orders)
    public void onViewClicked() {
        getActivity().getSupportFragmentManager().beginTransaction()
                .add(R.id.mainContent,
                        OrderDetailsFragment.newInstance("","")).addToBackStack("OrderDetailsFragment").commit();
    }
}
