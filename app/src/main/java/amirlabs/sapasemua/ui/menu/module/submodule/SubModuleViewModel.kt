package amirlabs.sapasemua.ui.menu.module.submodule

import amirlabs.sapasemua.base.DevViewModel
import amirlabs.sapasemua.data.model.Module
import amirlabs.sapasemua.data.repo.MainRepository
import amirlabs.sapasemua.utils.DevState
import amirlabs.sapasemua.utils.singleScheduler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.disposables.CompositeDisposable
import retrofit2.HttpException

class SubModuleViewModel (private val repo: MainRepository) : DevViewModel(){
    private val disposable = CompositeDisposable()

    private val _modules = MutableLiveData<DevState<Module>>(DevState.default())
    val modules: LiveData<DevState<Module>>
        get() = _modules

    fun getOneModule(moduleId: String){
        _modules.value = DevState.loading()
        repo.getOneModule(moduleId)
            .delay(1000L, java.util.concurrent.TimeUnit.MILLISECONDS)
            .compose(singleScheduler())
            .subscribe({
                if (it.data != null) _modules.value = DevState.success(it.data)
                else _modules.value = DevState.fail(null, it.message)
            },{
                if (it is HttpException) {
                    val errorBody = it.response()?.errorBody()?.string()
                    if (errorBody != null) {
                        _modules.value = DevState.Failure(null, errorBody)
                    }
                } else {
                    val errorMessage = it.localizedMessage
                    _modules.value = DevState.fail(null, errorMessage)
                }
            }).let(disposable::add)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}