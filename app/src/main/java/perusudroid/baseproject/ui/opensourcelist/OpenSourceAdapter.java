/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package perusudroid.baseproject.ui.opensourcelist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import perusudroid.baseproject.ui.base.BaseViewHolder;
import perusudroid.baseproject.data.model.api.response.openlist.Data;
import com.trisysit.baseproject_mvvm.databinding.ItemOpenSourceViewBinding;
import perusudroid.baseproject.ui.base.BaseRecyclerViewAdapter;
import perusudroid.baseproject.ui.base.IBaseRecyclerAdapterListener;

/**
 * Created by amitshekhar on 10/07/17.
 */

public class OpenSourceAdapter extends BaseRecyclerViewAdapter<Data> {

    private IBaseRecyclerAdapterListener listener;

    public void setListener(IBaseRecyclerAdapterListener listener) {
        setRetryListener(listener);
        this.listener = listener;
    }

    @Override
    protected RecyclerView.ViewHolder getViewHolder(ViewGroup parent) {
        ItemOpenSourceViewBinding openSourceViewBinding = ItemOpenSourceViewBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new OpenSourceViewHolder(openSourceViewBinding);
    }


    public class OpenSourceViewHolder extends BaseViewHolder<Data> implements View.OnClickListener {

        private final ItemOpenSourceViewBinding mBinding;

        public OpenSourceViewHolder(ItemOpenSourceViewBinding binding) {
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
        protected void bindData(Data data) {
            mBinding.titleTextView.setText(data.getTitle());
            mBinding.contentTextView.setText(data.getDescription());
            Glide.with(itemView.getContext())
                    .load(data.getImg_url())
                    .into(mBinding.coverImageView);
            mBinding.executePendingBindings();
        }

    }
}