package com.oy.test.dagger;

import android.content.Context;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.oy.test.network.MerchantService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mushoffa on 18/04/17.
 */

@Module
public class NetworkModule {

    private final int CACHE_DIR_SIZE = 10 * 1024 * 1024;

    private final String baseApiUrl;

    public NetworkModule(String baseApiUrl){
        this.baseApiUrl = baseApiUrl;
    }

    @Provides
    @Singleton
    public Cache provideCache(Context context){
        return new Cache(context.getExternalCacheDir(), CACHE_DIR_SIZE);
    }

    @Provides
    @Singleton
    public Gson provideGson(){
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                .create();
    }

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient(Cache cache){
        return new OkHttpClient.Builder()
                .cache(cache)
                .build();
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient){
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(baseApiUrl)
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    public MerchantService provideMerchantService(Retrofit retrofit){
        return retrofit.create(MerchantService.class);
    }
}
