package com.leeves.h.gdopera.exit;

import android.app.Activity;
import android.app.Application;

import java.util.LinkedList;
import java.util.List;

/**
 * Function：
 * Created by h on 2016/9/9.
 *
 * @author Leeves
 */
public class SysApplication extends Application {
    private List<Activity> mList = new LinkedList<>();
    private static SysApplication instance;

    private SysApplication(){

    }
    public synchronized static SysApplication getInstance() {
        if (instance == null) {
            instance = new SysApplication();
        }
        return instance;
    }

    public void addActivity(Activity activity) {
        mList.add(activity);
    }

    public void exit() {
        for (Activity activity : mList) {
            if (activity != null) {
                activity.finish();
            }
        }
        System.exit(0);
    }

    public void onLowMemory() {
        super.onLowMemory();
        System.exit(0);
    }
}
