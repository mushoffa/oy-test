package com.oy.test.dagger;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by mushoffa on 18/04/17.
 */

@Module
public final class ApplicationModule {

    private final Context context;

    public ApplicationModule(Context context){
        this.context = context;
    }

    @Provides
    Context provideContext(){
        return context;
    }
}
