package amirlabs.sapasemua.ui.auth.login

import amirlabs.sapasemua.base.DevViewModel
import amirlabs.sapasemua.data.model.BaseResponse
import amirlabs.sapasemua.data.model.User
import amirlabs.sapasemua.data.repo.MainRepository
import amirlabs.sapasemua.utils.DevState
import amirlabs.sapasemua.utils.PrefsKey
import amirlabs.sapasemua.utils.logError
import amirlabs.sapasemua.utils.prefs
import amirlabs.sapasemua.utils.singleScheduler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.JsonObject
import io.reactivex.rxjava3.disposables.CompositeDisposable
import retrofit2.HttpException
import java.util.concurrent.TimeUnit

class LoginViewModel(private val repo: MainRepository) : DevViewModel() {

    private val disposable = CompositeDisposable()
    private val _loginStatus = MutableLiveData<DevState<User>>(DevState.default())
    val loginStatus: LiveData<DevState<User>>
        get() = _loginStatus

    fun performLogin(email: String, password: String) {
        _loginStatus.value = DevState.loading()
        val body = mapOf(
            "email" to email,
            "password" to password
        )
        repo.login(body)
            .delay(1000L, TimeUnit.MILLISECONDS)
            .compose(singleScheduler())
            .subscribe({ user ->
                if (user.data == null) {
                    _loginStatus.value = DevState.fail(user.message?:"Error")
                } else {
                    prefs().setObject("user", user.data)
                    _loginStatus.value = DevState.success(user.data)
                    prefs().setBoolean(PrefsKey.IS_LOGGED_IN, true)
                }
            }, {
                logError(it.toString())
                if (it is HttpException) {
                    val errorBody = it.response()?.errorBody()?.string()
                    if (errorBody != null) {
                        _loginStatus.value = DevState.Failure(null, errorBody)
                    }
                } else {
                    val errorMessage = it.localizedMessage
                    _loginStatus.value = DevState.fail(null, errorMessage)
                }
            }).let(disposable::add)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}