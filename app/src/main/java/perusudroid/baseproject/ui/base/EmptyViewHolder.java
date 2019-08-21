package perusudroid.baseproject.ui.base;

import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.trisysit.baseproject_mvvm.R;

public class EmptyViewHolder extends BaseViewHolder implements View.OnClickListener {

    private Button btn_retry;

    public EmptyViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    protected void bindViews(View itemView) {
        btn_retry = itemView.findViewById(R.id.btn_retry);
        btn_retry.setOnClickListener(this);
    }

    @Override
    protected void bindData(Object data) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_retry:
                /*if(listener != null){
                    listener.onRetryClicked();
                }*/
                break;
        }
    }
}