package amirlabs.sapasemua

import amirlabs.sapasemua.utils.ApplicationContext
import android.os.Build
import android.support.multidex.MultiDexApplication

class SapaApplication: MultiDexApplication()  {
    override fun onCreate() {
        super.onCreate()
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            createNotificationChannel()
//        }
        ApplicationContext.getInstance().init(applicationContext)
    }
}