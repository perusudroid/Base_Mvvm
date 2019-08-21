package perusudroid.baseproject.receiver;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

public class StatusReceiver extends ResultReceiver {

    private Receiver mReceiver;

    public StatusReceiver(Handler handler) {
        super(handler);
    }

    public void setReceiver(Receiver mReceiver) {
        this.mReceiver = mReceiver;
    }

    public interface Receiver {
        void onReceiveResult(int resultCode, Bundle resultData);
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        super.onReceiveResult(resultCode, resultData);
        if (mReceiver != null) {
            mReceiver.onReceiveResult(resultCode, resultData);
        }
    }
}
