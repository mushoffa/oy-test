package com.oy.test.scheduler;

import android.support.annotation.NonNull;
import io.reactivex.Scheduler;


/**
 * Created by mushoffa on 18/04/17.
 */

public interface BaseSchedulerProvider {

    @NonNull
    Scheduler computation();

    @NonNull
    Scheduler io();

    @NonNull
    Scheduler immediate();

    @NonNull
    Scheduler ui();
}
