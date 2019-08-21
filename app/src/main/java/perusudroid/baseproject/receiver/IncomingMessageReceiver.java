package perusudroid.baseproject.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import perusudroid.baseproject.common.rx.SchedulerProvider;
import perusudroid.baseproject.data.IDataManagerHelper;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import io.reactivex.disposables.CompositeDisposable;

public class IncomingMessageReceiver extends BroadcastReceiver {

    private String TAG = IncomingMessageReceiver.class.getSimpleName();

    @Inject
    CompositeDisposable compositeDisposable;
    @Inject
    SchedulerProvider schedulerProvider;
    @Inject
    IDataManagerHelper iDataManager;


    @Override
    public void onReceive(Context context, Intent intent) {

        AndroidInjection.inject(this, context);

        Bundle data = intent.getExtras();

        Log.d(TAG, "onReceive: compositeDisposable " + (compositeDisposable == null) + " schedulerProvider " + (schedulerProvider == null) + " iDataManager " + (iDataManager == null));


    }

}
