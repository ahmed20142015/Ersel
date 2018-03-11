package ersel.greatbit.net.ersel.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ersel.greatbit.net.ersel.R;
import ersel.greatbit.net.ersel.http.HttpService;
import ersel.greatbit.net.ersel.http.IHttpService;
import ersel.greatbit.net.ersel.models.LoginResponse;
import ersel.greatbit.net.ersel.utilities.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {

    @BindView(R.id.user_mobile_number)
    AutoCompleteTextView userMobileNumber;
    @BindView(R.id.user_password)
    AutoCompleteTextView userPassword;
    @BindView(R.id.login)
    Button login;
    Unbinder unbinder;
    private IHttpService iHttpService;
    String mobileNumber,password;
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
        iHttpService = HttpService.createService(IHttpService.class);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.login)
    public void onViewClicked() {

        mobileNumber = userMobileNumber.getText().toString();
        password = userPassword.getText().toString();

        if(mobileNumber != null && password != null){

            loginUser();
        }
        else
            Toast.makeText(getActivity(), "Wrong Phone number or password", Toast.LENGTH_SHORT).show();

    }

    private void loginUser(){

        Call<LoginResponse> call = iHttpService.loginUser(mobileNumber,password);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body().getStatusCode().equalsIgnoreCase("100")){
                    SharedPrefManager.getInstance(getActivity()).setToken(response.body().getToken());
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .add(R.id.mainContent, OrdersFragment.newInstance("",""), "OrdersFragment")
                            .commit();
                }

                else
                    Toast.makeText(getActivity(), "Wrong Phone number or password", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });

    }

}
