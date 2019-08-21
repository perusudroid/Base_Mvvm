package perusudroid.baseproject.di.builder;


import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import perusudroid.baseproject.services.SyncApiService;

@Module
public abstract class ServiceBuilderModule {

    @ContributesAndroidInjector
    abstract SyncApiService contributeMyService();

}