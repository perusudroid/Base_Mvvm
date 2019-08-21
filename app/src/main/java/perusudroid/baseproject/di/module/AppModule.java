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

package perusudroid.baseproject.di.module;

import android.app.Application;
import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.trisysit.baseproject_mvvm.BuildConfig;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import perusudroid.baseproject.common.AppConstants;
import perusudroid.baseproject.common.rx.AppSchedulerProvider;
import perusudroid.baseproject.common.rx.SchedulerProvider;
import perusudroid.baseproject.data.AppDataManager;
import perusudroid.baseproject.data.IDataManagerHelper;
import perusudroid.baseproject.data.local.db.AppDBHelper;
import perusudroid.baseproject.data.local.db.AppDatabase;
import perusudroid.baseproject.data.local.db.IDBHelper;
import perusudroid.baseproject.data.local.prefs.IPrefHelper;
import perusudroid.baseproject.data.local.prefs.PrefHelperHelper;
import perusudroid.baseproject.data.remote.ApiInterface;
import perusudroid.baseproject.data.remote.DeApiService;
import perusudroid.baseproject.di.ApiInfo;
import perusudroid.baseproject.di.DatabaseInfo;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by amitshekhar on 07/07/17.
 */
@Module
public class AppModule {


    @Provides
    @Singleton
    ApiInterface provideApiInterface(@Named("normal") Retrofit retrofit) {
        return retrofit.create(ApiInterface.class);
    }

    @Provides
    @Singleton
    DeApiService provideDeApiService(@Named("de") Retrofit retrofit) {
        return retrofit.create(DeApiService.class);
    }


    @Provides
    @Singleton
    @Named("normal")
    Retrofit providedRetrofit(GsonConverterFactory gsonConverterFactory, RxJava2CallAdapterFactory rxJava2CallAdapterFactory, OkHttpClient okHttpClient) {
        return new Retrofit.Builder().baseUrl(BuildConfig.BASE_URL_MOCK)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJava2CallAdapterFactory)
                .client(okHttpClient)
                .build();
    }


    @Provides
    @Singleton
    @Named("de")
    Retrofit providedDeRetrofit(GsonConverterFactory gsonConverterFactory, RxJava2CallAdapterFactory rxJava2CallAdapterFactory, OkHttpClient okHttpClient) {
        return new Retrofit.Builder().baseUrl(BuildConfig.BASE_URL_LOAD)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJava2CallAdapterFactory)
                .client(okHttpClient)
                .build();
    }


    @Provides
    LinearLayoutManager provideLinearLayoutManager(Context context) {
        return new LinearLayoutManager(context);
    }

    @Provides
    @Singleton
    OkHttpClient providesOkHttpClient(Cache cache, HttpLoggingInterceptor okHttpLoggingInterceptor) {
        return new OkHttpClient.Builder()
                .cache(cache)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(okHttpLoggingInterceptor)
                .build();

    }

    @Provides
    @Singleton
    HttpLoggingInterceptor providesOkHttpLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    @Provides
    @Singleton
    Cache providesOkHttpCache(Context context) {
        int cacheSize = 10 * 1024 * 1024; //10mb
        return new Cache(context.getCacheDir(), cacheSize);
    }


    @Provides
    @Singleton
    GsonConverterFactory providesGsonConverterFactory() {
        return GsonConverterFactory.create();
    }

    @Provides
    @Singleton
    RxJava2CallAdapterFactory providesRxJavaCallAdapterFactory() {
        return RxJava2CallAdapterFactory.create();
    }

    @Provides
    @Singleton
    AppDatabase provideAppDatabase(@DatabaseInfo String dbName, Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, dbName)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }

    @Provides
    @DatabaseInfo
    String provideDatabaseName() {
        return AppConstants.Common.DB_NAME;
    }

    @Provides
    @Singleton
    IDataManagerHelper provideIDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }

    @Provides
    @Singleton
    AppDataManager providesAppDataManager(ApiInterface apiInterface,DeApiService deApiService, IDBHelper idbHelper, IPrefHelper iPrefHelper) {
        return new AppDataManager(apiInterface,deApiService, idbHelper, iPrefHelper);
    }


    @Provides
    @Singleton
    IDBHelper provideDbHelper(AppDBHelper appDbHelper) {
        return appDbHelper;
    }

    @Provides
    @ApiInfo
    String provideApiKey() {
        return BuildConfig.APPLICATION_ID;
    }


    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }


    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }

    @Provides
    @Singleton
    PrefHelperHelper providesPrefHelper() {
        return new PrefHelperHelper();
    }

    @Provides
    @Singleton
    IPrefHelper provideIPref(PrefHelperHelper prefHelper) {
        return prefHelper;
    }


    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }


    @Provides
    @Singleton
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

}
