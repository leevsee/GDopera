package com.leeves.h.gdopera.init;

import android.app.Application;
import android.widget.TabHost;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Function：
 * Created by h on 2016/9/14.
 *
 * @author
 */
public class FresrcoInit extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
