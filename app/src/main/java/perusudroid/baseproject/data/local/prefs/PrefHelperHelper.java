package perusudroid.baseproject.data.local.prefs;

import perusudroid.baseproject.data.model.api.response.login.LoginResponse;
import perusudroid.baseproject.common.AppConstants;

public class PrefHelperHelper extends SharedPref implements IPrefHelper {

    @Override
    public void updateLoginData(LoginResponse loginResponse) {
        setSharedValue(AppConstants.PrefKeys.LOGIN_DATA, loginResponse);
        setSharedValue(AppConstants.PrefKeys.IS_LOGGED_IN, true);
    }

    @Override
    public String getAuthToken() {
        if(isLoggedIn() && getLoginData() != null){
            return getLoginData().getAccessToken();
        }
        return null;
    }

    @Override
    public Boolean isLoggedIn() {
        return getBooleanValue(AppConstants.PrefKeys.IS_LOGGED_IN);
    }


    private LoginResponse getLoginData() {
        return (LoginResponse) getObject(AppConstants.PrefKeys.LOGIN_DATA, LoginResponse.class);
    }
}
