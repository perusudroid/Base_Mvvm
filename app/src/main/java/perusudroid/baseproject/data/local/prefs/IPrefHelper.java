package perusudroid.baseproject.data.local.prefs;

import perusudroid.baseproject.data.model.api.response.login.LoginResponse;

public interface IPrefHelper {

    void updateLoginData(LoginResponse loginResponse);
    String getAuthToken();
    Boolean isLoggedIn();

}
