package mehroz.sis.com.androiddevelopmenttest.storage;

import android.annotation.SuppressLint;
import android.content.Context;

public class Preferences {

    private static Preferences sharedPrefsHelper;
    private Context context;
    private android.content.SharedPreferences sharedPreferences;
    private android.content.SharedPreferences.Editor editor;
    private static final String APP_PREFERENCE_NAME = "APP_PREFS";

    @SuppressLint("CommitPrefEdits")
    private Preferences(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(APP_PREFERENCE_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static synchronized Preferences getInstance(Context context) {
        if (sharedPrefsHelper == null)
            sharedPrefsHelper = new Preferences(context);
        return sharedPrefsHelper;
    }

    public void setStringPref(String key, String value) {
        editor.putString(key, value);
        editor.apply();
    }

    public String getStringPref(String key) {
        String result = "n/a";
        if (sharedPreferences != null)
            result = sharedPreferences.getString(key, "");
        return result;
    }

    public void setIntegerPref(String key, int value) {
        editor.putInt(key, value);
        editor.apply();
    }

    public Integer getIntegerPref(String key) {
        int result = -1;
        if (sharedPreferences != null)
            result = sharedPreferences.getInt(key, -1);
        return result;
    }

    public void setFloatPref(String key, Float value) {
        editor.putFloat(key, value);
        editor.apply();
    }


    public Float getFloatPref(String key) {
        float result = 0;
        if (sharedPreferences != null)
            result = sharedPreferences.getFloat(key, -1);
        return result;
    }

    public void setBooleanPref(String key, Boolean value) {
        editor.putBoolean(key, value);
        editor.apply();
    }


    public Boolean getBooleanPref(String key) {
        Boolean result = false;
        if (sharedPreferences != null)
            result = sharedPreferences.getBoolean(key, false);
        return result;
    }



}
