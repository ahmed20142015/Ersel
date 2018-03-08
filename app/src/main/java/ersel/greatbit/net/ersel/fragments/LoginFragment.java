package ersel.greatbit.net.ersel.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ersel.greatbit.net.ersel.R;

public class LoginFragment extends Fragment {

    @BindView(R.id.user_mobile_number)
    AutoCompleteTextView userMobileNumber;
    @BindView(R.id.user_password)
    AutoCompleteTextView userPassword;
    @BindView(R.id.login)
    Button login;
    Unbinder unbinder;

    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.login)
    public void onViewClicked() {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.mainContent, OrdersFragment.newInstance("",""), "OrdersFragment")
                .commit();
    }
}
