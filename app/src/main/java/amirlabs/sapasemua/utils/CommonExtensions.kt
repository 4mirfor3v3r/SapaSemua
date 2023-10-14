package amirlabs.sapasemua.utils

import amirlabs.sapasemua.base.ViewModelFactory
import amirlabs.sapasemua.data.local.DevPreferenceManager
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.google.gson.Gson

inline fun <reified T : ViewModel> ViewModelStoreOwner.getViewModel(): Lazy<T> {
    return lazy { ViewModelProvider(this, ViewModelFactory.viewModelFactory)[T::class.java] }
}

fun prefs(context:Context?=null): DevPreferenceManager {
    return DevPreferenceManager(context?:ApplicationContext.get(), "amirlabs.sapasemua.prefs", Gson())
}

fun logDebug(vararg message: String?) {
    Log.d("TAG_DEBUG", message.toList().toString())
}

fun logError(vararg message: String?) {
    Log.e("TAG_ERROR", message.toList().toString())
}