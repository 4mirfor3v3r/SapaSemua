package amirlabs.sapasemua;

import android.support.multidex.MultiDexApplication;

import amirlabs.sapasemua.utils.ApplicationContext;

public class SapaSemuaApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationContext.getInstance().init(getApplicationContext());
    }
}
