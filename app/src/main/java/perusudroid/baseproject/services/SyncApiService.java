package perusudroid.baseproject.services;

import android.app.ActivityManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import android.os.StrictMode;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.trisysit.baseproject_mvvm.R;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import io.reactivex.disposables.CompositeDisposable;
import perusudroid.baseproject.common.AppConstants;
import perusudroid.baseproject.common.rx.SchedulerProvider;
import perusudroid.baseproject.data.IDataManagerHelper;

import static androidx.core.app.NotificationCompat.PRIORITY_MIN;


public class SyncApiService extends IntentService {

    private String TAG = SyncApiService.class.getSimpleName();
    private Timer timer;

    @Inject
    CompositeDisposable compositeDisposable;
    @Inject
    SchedulerProvider schedulerProvider;
    @Inject
    IDataManagerHelper iDataManager;
    @Inject
    Context mContext;
    Bundle bundle = new Bundle();

    ResultReceiver receiver;
    private Handler mHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        AndroidInjection.inject(this);
        startForeground();
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        if (intent != null && !isAppIsInBackground(mContext)) {
            receiver = intent.getParcelableExtra(AppConstants.BundleKeys.RECEIVER);
        } else {
            receiver = null;
        }


        doStartTimer();
        return Service.START_STICKY;
    }


    public SyncApiService() {
        super("SyncApiService");
    }


    private void startForeground() {
        String channelId = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            channelId = createNotificationChannel();
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId);
        Notification notification = notificationBuilder.setOngoing(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(PRIORITY_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();
        startForeground(101, notification);
    }

    /*to getting channel id*/
    @RequiresApi(Build.VERSION_CODES.O)
    private String createNotificationChannel() {
        String channelId = "SyncApiService";
        String channelName = "SyncBackApiService";
        NotificationChannel chan = new NotificationChannel(channelId,
                channelName, NotificationManager.IMPORTANCE_NONE);
        chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        NotificationManager service = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        service.createNotificationChannel(chan);
        return channelId;
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Log.d(TAG, "onHandleIntent: compositeDisposable " + (compositeDisposable == null) + " schedulerProvider " + (schedulerProvider == null) + " iDataManager " + (iDataManager == null));

        if (intent != null) {
            receiver = intent.getParcelableExtra(AppConstants.BundleKeys.RECEIVER);
        }

    }

    private void doStartTimer() {

        timer = new Timer();
        TimerTask hourlyTask = new TimerTask() {
            @Override
            public void run() {
                Log.d(TAG, "run: task is running");
                fetchAndUpdateList();
            }
        };

        timer.schedule(hourlyTask, 0l, 5000); //every five seconds
    }


    private void fetchAndUpdateList() {

        Log.d(TAG, "fetchAndUpdateList: receiver is null ? " + (receiver == null));
        if (receiver != null) {
            receiver.send(AppConstants.StatusCodes.RUNNING, bundle);
        }

        mHandler = new Handler(Looper.getMainLooper());

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (receiver != null) {
                    receiver.send(AppConstants.StatusCodes.FINISHED, bundle);
                }
            }
        }, 2000);
    }


    private void showMessage(String msg) {

        if (bundle != null) {
            bundle.putString(AppConstants.BundleKeys.DATA, msg);
        }
        if (receiver != null) {
            receiver.send(AppConstants.StatusCodes.RETURN_RESPONSE, bundle);
        }

    }

    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
            if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                for (String activeProcess : processInfo.pkgList) {
                    if (activeProcess.equals(context.getPackageName())) {
                        isInBackground = false;
                    }
                }
            }
        }

        return isInBackground;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: in servicve");

        if(timer != null){
            timer.cancel();
        }

        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
        compositeDisposable.clear();
    }
}
