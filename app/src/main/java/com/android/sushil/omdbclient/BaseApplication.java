package com.android.sushil.omdbclient;

import android.app.Application;

import com.android.sushil.omdbclient.injection.component.ActivityComponent;
import com.android.sushil.omdbclient.injection.component.DaggerActivityComponent;
import com.android.sushil.omdbclient.injection.module.NetworkModule;

import java.io.File;

/**
 * Created by sushiljha on 09/09/2017.
 */

public class BaseApplication extends Application {
    ActivityComponent mActivityComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        File cacheFile = new File(getCacheDir(), "responses");
        mActivityComponent = DaggerActivityComponent.builder().networkModule(new NetworkModule(cacheFile)).build();

    }

    public ActivityComponent getDependency() {
        return mActivityComponent;
    }
}
