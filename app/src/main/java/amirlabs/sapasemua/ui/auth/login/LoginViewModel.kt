package amirlabs.sapasemua.ui.auth.login

import amirlabs.sapasemua.base.DevViewModel
import amirlabs.sapasemua.data.model.User
import amirlabs.sapasemua.data.repo.MainRepository
import amirlabs.sapasemua.utils.DevState
import amirlabs.sapasemua.utils.prefs
import amirlabs.sapasemua.utils.singleScheduler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.disposables.CompositeDisposable
import retrofit2.HttpException

class LoginViewModel(private val repo: MainRepository) : DevViewModel() {

    private val disposable = CompositeDisposable()
    private val _loginStatus = MutableLiveData<DevState<Any>>(DevState.default())
    val loginStatus: LiveData<DevState<Any>>
        get() = _loginStatus

    fun performLogin(email: String, password: String) {
        _loginStatus.value = DevState.loading()
        val fcmToken = prefs().getString("fcm_token","")
        val body = mapOf(
            "email" to email,
            "password" to password,
            "fcm_token" to fcmToken
        )
        repo.login(body)
            .compose(singleScheduler())
            .subscribe({ user ->
                if (user.data == null) {
                    _loginStatus.value = DevState.fail(user.message?:"Error")
                } else {
                    _loginStatus.value = DevState.success(user.data)
                }
            }, {
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