package perusudroid.baseproject.di.builder;


import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import perusudroid.baseproject.receiver.IncomingMessageReceiver;

@Module
public abstract class ReceiverBuilderModule {

    @ContributesAndroidInjector
    abstract IncomingMessageReceiver incomingMessageReceiver();

}