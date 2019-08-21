package perusudroid.baseproject.ui.base;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.trisysit.baseproject_mvvm.R;

import java.util.ArrayList;
import java.util.List;

public abstract class BasePagedRecyclerViewAdapter<T> extends PagedListAdapter<T, RecyclerView.ViewHolder> {


    protected String TAG = getClass().getSimpleName();
    private static final int VIEW_TYPE_LOADING = 0;
    private static final int VIEW_TYPE_NORMAL = 1;
    private static final int VIEW_TYPE_LOAD_MORE = 2;
    private static final int VIEW_TYPE_EMPTY = 3;
    private List<T> data;
    private IBaseRecyclerAdapterListener listener;

    public BasePagedRecyclerViewAdapter(DiffUtil.ItemCallback<T> DIFF_CALLBACK) {
        super(DIFF_CALLBACK);
        data = new ArrayList<>();
    }


    public void setRetryListener(IBaseRecyclerAdapterListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Log.d(TAG, "onCreateViewHolder: viewType " + viewType);

        if (viewType == VIEW_TYPE_LOADING) {
            return new ProgressLoadingViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.inflater_progress_loading, parent, false));
        } else if (viewType == VIEW_TYPE_LOAD_MORE) {
            return new LoadingViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.inflater_loading, parent, false));
        } else if (viewType == VIEW_TYPE_EMPTY) {
            return new EmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.inflater_empty, parent, false));
        }

        return getViewHolder(parent);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (data != null && data.size() > 0) {

            if ((holder instanceof BaseViewHolder)) {
                //noinspection unchecked
                ((BaseViewHolder) holder).bindData(data.get(position));
            }
        }


    }

    protected abstract RecyclerView.ViewHolder getViewHolder(ViewGroup parent);


    @Override
    public int getItemCount() {
        if (data != null && !data.isEmpty()) {
            return data.size();
        } else {
            return 1;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (data == null || data.isEmpty()) {

            return VIEW_TYPE_LOADING;

        } else if (data.get(position) != null &&
                data.get(position) instanceof Integer &&
                ((Integer) data.get(position)) == -1
        ) {
            return VIEW_TYPE_EMPTY;

        } else if (data.get(position) == null) {

            return VIEW_TYPE_LOAD_MORE;
        }

        return VIEW_TYPE_NORMAL;
    }

    public void addItem(T object) {
        data.add(object);
        notifyItemInserted(data.indexOf(object));
    }

    public void removeItem(int position) throws IndexOutOfBoundsException {
        data.remove(position);
        notifyItemRemoved(position);
    }


    public void addItems(List<T> data) {
        if (data != null) {
            int startRange = (this.data.size() - 1) > 0 ? data.size() - 1 : 0;
            this.data.addAll(data);
            notifyItemRangeChanged(startRange, data.size());
        }
    }


    public void resetItems(List<T> data) {
        if (data != null) {
            this.data = data;
            notifyDataSetChanged();
        }
    }

    public void addEmptyParam() {
        data.clear();
        data.add(convertInstanceOfObject(-1));
        notifyDataSetChanged();
    }


    public static <T> T convertInstanceOfObject(Object o) {
        try {
            return (T) o;
        } catch (ClassCastException e) {
            return null;
        }
    }

    class ProgressLoadingViewHolder extends BaseViewHolder {

        public ProgressLoadingViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        protected void bindViews(View itemView) {

        }

        @Override
        protected void bindData(Object data) {

        }
    }

    class LoadingViewHolder extends BaseViewHolder {

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        protected void bindViews(View itemView) {

        }

        @Override
        protected void bindData(Object data) {

        }

    }

    class EmptyViewHolder extends BaseViewHolder implements View.OnClickListener {

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
            switch (v.getId()) {
                case R.id.btn_retry:
                    if (listener != null) {
                        listener.onRetryClicked();
                    }
                    break;
            }
        }
    }

}
