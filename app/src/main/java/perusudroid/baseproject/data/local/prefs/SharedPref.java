package perusudroid.baseproject.data.local.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;
import java.util.List;


public class SharedPref {


    // Single ton objects...
    private static SharedPreferences preference = null;
    private static SharedPref sharedPref = null;

    /**
     *
     * @param mContext - Init SharedPreferences
     */

    public static void init(Context mContext) {
        if (preference == null) {
            preference = mContext.getSharedPreferences(mContext.getPackageName(), Context.MODE_PRIVATE);
        }
    }

    /**
     *
     * @return - SharedPref Instance
     */
    public static SharedPref getInstance() {
        if (sharedPref == null) {
            sharedPref = new SharedPref();
        }
        return sharedPref;
    }

    /**
     *
     * @return - Not-Null preference instance ; Throws exception if not initialized first
     */

    private static SharedPreferences getPreferenceInstance() {
        if (preference != null) {
            return preference;
        } else {
            throw new RuntimeException("Initialize SharedPref first");
        }
    }

    /**
     * Set the Integer value in the shared preference W.R.T the given key.
     *
     * @param key   String used as a key for accessing the value.
     * @param value String which is to be stored in shared preference.
     */

    public void setSharedValue(String key, String value) {

        if (getPreferenceInstance() != null) {
            Editor editor = getPreferenceInstance().edit();
            editor.putString(key, value);
            editor.apply();
        }
    }

    /**
     * Set the Integer value in the shared preference W.R.T the given key.
     *
     * @param key   String used as a key for accessing the value.
     * @param value Integer value which is to be stored in shared preference.
     */
    public void setSharedValue(String key, int value) {
        if (getPreferenceInstance() != null) {
            Editor editor = getPreferenceInstance().edit();
            editor.putInt(key, value);
            editor.apply();
        }
    }

    /**
     * Set the boolean value in the shared preference W.R.T the given key.
     *
     * @param key   String used as a key for accessing the value.
     * @param value Boolean value which is to be stored in shared preference.
     */
    public void setSharedValue(String key, boolean value) {
        if (getPreferenceInstance() != null) {
            Editor editor = getPreferenceInstance().edit();
            editor.putBoolean(key, value);
            editor.apply();
        }
    }

    /**
     * Set the boolean value in the shared preference W.R.T the given key.
     *
     * @param key   String used as a key for accessing the value.
     * @param value Long value which is to be stored in shared preference.
     */

    public void setSharedValue(String key, long value) {
        if (getPreferenceInstance() != null) {
            Editor editor = getPreferenceInstance().edit();
            editor.putLong(key, value);
            editor.apply();
        }

    }


    /**
     * Set the Integer value in the shared preference W.R.T the given key.
     *
     * @param key   String used as a key for accessing the value.
     * @param value List<T> data which is to be stored in shared preference.
     */

    public static <T> T setSharedList(String key, List<T> value) {
        if (getPreferenceInstance() != null) {
            Gson gson = new Gson();
            String json = gson.toJson(value);
            Editor editor = getPreferenceInstance().edit();
            editor.putString(key, json);
            editor.apply();
        }
        return null;
    }


    /**
     * Set the Integer value in the shared preference W.R.T the given key.
     *
     * @param key   String used as a key for accessing the value.
     * @param value Object which is to be stored in shared preference.
     */

    public  void setSharedValue(String key, Object value) {
        if (getPreferenceInstance() != null) {
            Gson gson = new GsonBuilder().create();
            getPreferenceInstance().edit().putString(key, gson.toJson(value)).apply();
        }
    }




    /**
     * Returns Integer value for the given key.
     * By default it will return false.
     *
     * @param key String used as a key for accessing the value.
     * @return false by default; returns the boolean value for the given key.
     */

    public Boolean getBooleanValue(String key) {
        return getPreferenceInstance().getBoolean(key, false);
    }

    /**
     * Returns Integer value for the given key.
     * By default it will return "-1".
     *
     * @param key String used as a key for accessing the value.
     * @return -1 by default; returns the Integer value for the given key.
     */

    public int getIntValue(String key) {
        return getPreferenceInstance().getInt(key, -1);
    }

    /**
     * Returns String value for the given key.
     * By default it will return null.
     *
     * @param key String used as a key for accessing the value.
     * @return null by default; returns the String value for the given key.
     */

    public String getStringValue(String key) {
        if (getPreferenceInstance() != null) {
            return getPreferenceInstance().getString(key, null);
        }
        return null;
    }

    /**
     *
     * @param clazz - List Type Class
     * @param key  - String used as a key for accessing the value.
     * @param <T> - Generic Type
     * @return
     */

    public static <T> List<T> getSharedList(final Class<T[]> clazz, String key) {

        if (getPreferenceInstance() != null) {
            if (getPreferenceInstance().getString(key, null) != null) {
                final T[] jsonToObject = new Gson().fromJson(getPreferenceInstance().getString(key, ""), clazz);
                return Arrays.asList(jsonToObject);
            }
        }
        return null;
    }

    /**
     *
     * @param key - String used as a key for accessing the value.
     * @param tClass - Return Object Class Type
     * @param <T> - Generic Type
     * @return
     */

    public static <T> Object getObject(String key, Class<?> tClass) {
        try {
            Gson gson = new GsonBuilder().create();
            return gson.fromJson(getInstance().getStringValue(key), tClass);
        } catch (Exception e) {
            Log.e("gson", e.getMessage());
            return null;
        }
    }


    public void clearKey(String key){
        if (getPreferenceInstance() != null) {
            Editor editor = getPreferenceInstance().edit();
            editor.remove(key);
            editor.apply();
        }
    }


    public void clearAll() {
        if (getPreferenceInstance() != null) {
            Editor editor = getPreferenceInstance().edit();
            editor.clear();
            editor.apply();
        }

    }


}