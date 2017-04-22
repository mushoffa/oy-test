package com.oy.test.common.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by mushoffa on 21/04/17.
 */

public abstract class BaseActivity extends AppCompatActivity{

    protected final String TAG = getClass().getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.bind(this);
    }

    @LayoutRes
    public abstract int getLayout();
}
