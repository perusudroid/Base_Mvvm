package perusudroid.baseproject.common;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.BroadcastReceiver;


import dagger.android.HasBroadcastReceiverInjector;
import dagger.android.HasServiceInjector;
import perusudroid.baseproject.data.local.prefs.SharedPref;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import perusudroid.baseproject.di.component.DaggerAppComponent;

public class BaseProject extends Application implements HasActivityInjector, HasServiceInjector, HasBroadcastReceiverInjector {

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;
    @Inject
    DispatchingAndroidInjector<Service> serviceDispatchingAndroidInjector;
    @Inject
    DispatchingAndroidInjector<BroadcastReceiver> bcDispatchingAndroidInjector;


    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }

    @Override
    public AndroidInjector<Service> serviceInjector() {
        return serviceDispatchingAndroidInjector;
    }

    @Override
    public AndroidInjector<BroadcastReceiver> broadcastReceiverInjector() {
        return bcDispatchingAndroidInjector;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPref.init(this);
        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this);

    }


}
