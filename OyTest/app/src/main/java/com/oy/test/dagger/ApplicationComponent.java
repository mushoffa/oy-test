package com.oy.test.dagger;

import com.oy.test.activity.HomeActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by mushoffa on 18/04/17.
 */

@Singleton
@Component(modules = {
        ApplicationModule.class,
        NetworkModule.class
})
public interface ApplicationComponent {

    void inject(HomeActivity activity);
}
