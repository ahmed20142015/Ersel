package ersel.greatbit.net.ersel.utilities;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Eslam on 3/11/2018.
 */

public class SharedPrefManager {
    private static final String SHARES_PREF_NAME = "sharedPref";
    private static final String KEY_TOKEN = "keytoken";
    private static final String KEY_SEND_TOKEN = "keysendtoken";
    private static final String KEY_REFRESH_TOKEN = "keyrefreshtoken";
    private static SharedPrefManager sharedPrefManager;
    private static Context mContext;
    SharedPreferences sharedPref ;
    private SharedPrefManager (Context context){
        mContext=context;
        sharedPref = mContext.getSharedPreferences(SHARES_PREF_NAME,Context.MODE_PRIVATE);
    }

    public static synchronized SharedPrefManager getInstance(Context context){
        if(sharedPrefManager == null){
            sharedPrefManager = new SharedPrefManager(context);
        }

        return sharedPrefManager;
    }

    public boolean isLogin(){
        return sharedPref.getString(KEY_TOKEN,null) != null ;
    }

    public void setToken(String token){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(KEY_TOKEN,token);
        editor.apply();
    }

    public String getToken(){
        return sharedPref.getString(KEY_TOKEN,"");
    }

    public void setSendToken(boolean sendToken){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(KEY_SEND_TOKEN,sendToken);
        editor.apply();
    }

    public boolean getSendToken(){
        return sharedPref.getBoolean(KEY_SEND_TOKEN,false);
    }




}
