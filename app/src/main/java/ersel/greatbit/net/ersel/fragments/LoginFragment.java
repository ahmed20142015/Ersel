package ersel.greatbit.net.ersel.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ersel.greatbit.net.ersel.R;
import ersel.greatbit.net.ersel.http.HttpService;
import ersel.greatbit.net.ersel.http.IHttpService;
import ersel.greatbit.net.ersel.models.LoginResponse;
import ersel.greatbit.net.ersel.utilities.ConnectionDetector;
import ersel.greatbit.net.ersel.utilities.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {

    @BindView(R.id.user_mobile_number)
    EditText userMobileNumber;
    @BindView(R.id.user_password)
    EditText userPassword;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.login_progress)
    ProgressBar progress_login;
    Unbinder unbinder;
    private IHttpService iHttpService;
    String mobileNumber,password;
    public static LoginFragment newInstance() {
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

        mobileNumber = userMobileNumber.getText().toString().trim();
        password = userPassword.getText().toString().trim();
        Log.w("mobile",mobileNumber);
        Log.w("password",password);

        if(!mobileNumber.equalsIgnoreCase("")   && !password.equalsIgnoreCase("")){
            if(ConnectionDetector.getInstance(getActivity()).isConnectingToInternet())
                loginUser();
            else
                Toast.makeText(getActivity(), "Please Check Internet Connection", Toast.LENGTH_SHORT).show();

        }
        else{
            userMobileNumber.setError("Please enter mobile number");
            userPassword.setError("Please enter password");
        }

    }

    private void loginUser(){
        progress_login.setVisibility(View.VISIBLE);
        Call<LoginResponse> call = iHttpService.loginUser(mobileNumber,password);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body().getStatusCode().equalsIgnoreCase("100")){
                    progress_login.setVisibility(View.GONE);
                    SharedPrefManager.getInstance(getActivity()).setToken(response.body().getToken());
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .add(R.id.mainContent, OrdersFragment.newInstance("",""), "OrdersFragment")
                            .commit();
                }

                else{
                    progress_login.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Wrong Phone number or password", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progress_login.setVisibility(View.GONE);
            }
        });

    }

}
