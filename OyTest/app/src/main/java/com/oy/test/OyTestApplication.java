package com.oy.test;

import android.app.Application;

import com.oy.test.dagger.ApplicationComponent;
import com.oy.test.dagger.ApplicationModule;

import io.realm.Realm;

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

        Realm.init(this);
    }

    public ApplicationComponent getComponent(){
        return applicationComponent;
    }
}
