package perusudroid.baseproject.ui.photos;

import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.trisysit.baseproject_mvvm.R;
import com.trisysit.baseproject_mvvm.databinding.ActivityPhotosBinding;

import javax.inject.Inject;

import perusudroid.baseproject.data.model.other.NetworkState;
import perusudroid.baseproject.ui.base.BaseActivity;
import perusudroid.baseproject.ui.base.ViewModelProviderFactory;
import perusudroid.baseproject.ui.opensourcelist.OpenListActivity;
import perusudroid.baseproject.ui.sync.SyncApiActivity;

public class PhotosActivity extends BaseActivity<ActivityPhotosBinding, PhotosViewModel> implements IPhotosView {

    @Inject
    ViewModelProviderFactory factory;
    PhotosAdapter photosAdapter;
    @Inject
    LinearLayoutManager manager;

    private PhotosViewModel viewModel;
    private ActivityPhotosBinding binding;


    @Override
    protected void initStuffs() {
        binding = getViewDataBinding();
        photosAdapter = new PhotosAdapter();
        photosAdapter.setListener(this);
        viewModel.setView(this);
    }


    @Override
    protected void setAssets() {
        binding.recyclerView.setLayoutManager(manager);
        binding.recyclerView.setAdapter(photosAdapter);

        binding.includedLay.btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewModel != null) {
                    viewModel.doConfigureAndFetch();
                }
            }
        });

        viewModel.itemPagedList.observe(this, data -> {
            if (data != null) {
                photosAdapter.submitList(data);
                binding.vsParent.setChildVisible();
            }
        });

        viewModel.initialLoad.observe(this, new Observer<NetworkState>() {
            @Override
            public void onChanged(NetworkState networkState) {
                Log.d(TAG, "onChanged: " + networkState.getStatus());

                switch (networkState.getStatus()) {
                    case FAILED:
                    case EMPTY:
                        //no data
                        binding.vsParent.setChildVisible();
                        binding.vsChild.setChildVisible();
                        break;
                    case NO_LOAD_MORE:
                        Toast.makeText(PhotosActivity.this, "No more data to load!", Toast.LENGTH_SHORT).show();
                        break;
                }

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_settings_1:
                startActivity(new Intent(this, OpenListActivity.class));
                break;
            case R.id.action_settings_2:
                startActivity(new Intent(this, SyncApiActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public PhotosViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(PhotosViewModel.class);
        return viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_photos;
    }

    @Override
    public void handleError(Throwable throwable) {

    }
}
