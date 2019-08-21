package perusudroid.baseproject.common;

public class AppConstants {

    public @interface BundleKeys{
        String DATA = "DATA";
        String RECEIVER = "RECEIVER";
    }

    public @interface PrefKeys{
        String IS_LOGGED_IN = "LOGIN_DATA";
        String LOGIN_DATA = "LOGIN_DATA";
    }


    public @interface StatusCodes{
        int EMPTY_RESULTS = 0;
        int RUNNING = 1;
        int FINISHED = 2;
        int RETURN_RESPONSE = 3;
    }

    public @interface Common{
        String DB_NAME = "baseproject.db";
        String PREF_NAME = "baseproject_pref";
    }


}
