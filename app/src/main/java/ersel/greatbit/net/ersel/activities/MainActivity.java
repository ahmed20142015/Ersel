package ersel.greatbit.net.ersel.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ersel.greatbit.net.ersel.R;
import ersel.greatbit.net.ersel.fragments.LoginFragment;
import ersel.greatbit.net.ersel.fragments.OrdersFragment;
import ersel.greatbit.net.ersel.utilities.SharedPrefManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(SharedPrefManager.getInstance(this).isLogin()){
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.mainContent, OrdersFragment.newInstance("",""), "OrdersFragment")
                    .commit();
        }
        else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.mainContent, LoginFragment.newInstance("", ""), "LoginFragment")
                    .commit();
        }
    }
}
