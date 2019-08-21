package perusudroid.baseproject.ui.photos;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import perusudroid.baseproject.ui.base.BaseViewHolder;
import perusudroid.baseproject.ui.base.IBaseRecyclerAdapterListener;
import perusudroid.baseproject.data.model.api.response.stack.Items;
import com.trisysit.baseproject_mvvm.databinding.InflaterPhotos2Binding;
import com.trisysit.baseproject_mvvm.databinding.InflaterPhotosBinding;


public class PhotosAdapter extends PagedListAdapter<Items, RecyclerView.ViewHolder> {

    private IBaseRecyclerAdapterListener listener;

    private static DiffUtil.ItemCallback<Items> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Items>() {
                @Override
                public boolean areItemsTheSame(Items oldItem, Items newItem) {
                    return oldItem.getQuestion_id().equals(newItem.getQuestion_id());
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(Items oldItem, Items newItem) {
                    return oldItem.equals(newItem);
                }
            };


    public PhotosAdapter() {
        super(DIFF_CALLBACK);
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2 == 0 ? 1 : 2;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Log.d("PhotosAdapter", "onCreateViewHolder: viewType " + viewType);

        if (viewType == 1) {
            InflaterPhotosBinding photosBinding = InflaterPhotosBinding
                    .inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new PhotosViewHolder(photosBinding);
        } else if (viewType == 2) {
            InflaterPhotos2Binding photosBinding = InflaterPhotos2Binding
                    .inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new Photos2ViewHolder(photosBinding);
        }

        InflaterPhotos2Binding photosBinding = InflaterPhotos2Binding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new Photos2ViewHolder(photosBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PhotosViewHolder) {
            ((PhotosViewHolder) holder).bindData(getItem(position));
        } else if (holder instanceof Photos2ViewHolder) {
            ((Photos2ViewHolder) holder).bindData(getItem(position));
        }
    }


    public void setListener(IBaseRecyclerAdapterListener listener) {
        //setRetryListener(listener);
        this.listener = listener;
    }

    public class PhotosViewHolder extends BaseViewHolder<Items> implements View.OnClickListener {

        private final InflaterPhotosBinding mBinding;

        public PhotosViewHolder(InflaterPhotosBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onClick(View view) {
            listener.onClickItem(itemView.getTag());
        }

        @Override
        protected void bindViews(View itemView) {
            //  mBinding.contentTextView.setOnClickListener(this);
        }

        @Override
        protected void bindData(Items items) {
            mBinding.tvName.setText(items.getOwner().getDisplay_name());
            mBinding.tvLink.setText(items.getOwner().getLink());
            Glide.with(itemView.getContext())
                    .load(items.getOwner().getProfile_image())
                    .into(mBinding.ivPic);
            mBinding.executePendingBindings();
        }

    }

    public class Photos2ViewHolder extends BaseViewHolder<Items> implements View.OnClickListener {

        private final InflaterPhotos2Binding mBinding;

        public Photos2ViewHolder(InflaterPhotos2Binding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onClick(View view) {
            listener.onClickItem(itemView.getTag());
        }

        @Override
        protected void bindViews(View itemView) {
            //  mBinding.contentTextView.setOnClickListener(this);
        }

        @Override
        protected void bindData(Items items) {
            mBinding.tvName.setText(items.getOwner().getDisplay_name());
            mBinding.tvLink.setText(items.getOwner().getLink());
            Glide.with(itemView.getContext())
                    .load(items.getOwner().getProfile_image())
                    .into(mBinding.ivPic);
            mBinding.executePendingBindings();
        }

    }


}