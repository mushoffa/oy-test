package com.oy.test;

import android.app.Application;

import com.oy.test.dagger.ApplicationComponent;
import com.oy.test.dagger.ApplicationModule;
import com.oy.test.dagger.DaggerApplicationComponent;
import com.oy.test.dagger.NetworkModule;

/**
 * Created by mushoffa on 18/04/17.
 */

public class OyTestApplication extends Application{

    ApplicationComponent applicationComponent;

    @Override
    public void onCreate(){
        super.onCreate();
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .networkModule(new NetworkModule(this.getResources().getString(R.string.api_url)))
                .build();
    }

    public ApplicationComponent getComponent(){
        return applicationComponent;
    }
}
