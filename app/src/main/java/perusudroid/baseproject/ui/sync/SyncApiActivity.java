package perusudroid.baseproject.ui.sync;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.trisysit.baseproject_mvvm.R;
import com.trisysit.baseproject_mvvm.databinding.ActivitySyncApiBinding;

import perusudroid.baseproject.common.AppConstants;
import perusudroid.baseproject.receiver.StatusReceiver;
import perusudroid.baseproject.services.SyncApiService;

public class SyncApiActivity extends AppCompatActivity implements View.OnClickListener, StatusReceiver.Receiver {

    private ActivitySyncApiBinding binding;
    private StatusReceiver mStatusReceiver;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sync_api);
        binding.btnStart.setOnClickListener(this);
        binding.btnStop.setOnClickListener(this);

        mHandler = new Handler();
        mStatusReceiver = new StatusReceiver(mHandler);
        mStatusReceiver.setReceiver(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStart:
                if (!isServiceRunning(SyncApiService.class)) {
                    Intent mIntent = new Intent(this, SyncApiService.class);
                    mIntent.putExtra(AppConstants.BundleKeys.RECEIVER, mStatusReceiver);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        startForegroundService(mIntent);
                    } else {
                        startService(mIntent);
                    }
                }
                break;
            case R.id.btnStop:
                if (isServiceRunning(SyncApiService.class)) {
                    stopService(new Intent(this, SyncApiService.class));
                }
                break;
        }
    }

    public boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        if (manager != null) {
            for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
                if (serviceClass.getName().equals(service.service.getClassName())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        Log.d("SyncApiActivity", "onReceiveResult: resultCode "+ resultCode);
        switch (resultCode) {
            case AppConstants.StatusCodes.EMPTY_RESULTS:
                break;
            case AppConstants.StatusCodes.RUNNING:
                binding.tvStatus.setText(R.string.running);
                break;
            case AppConstants.StatusCodes.FINISHED:
                binding.tvStatus.setText(R.string.completed);
                break;
            case AppConstants.StatusCodes.RETURN_RESPONSE:
                break;
        }
    }
}
