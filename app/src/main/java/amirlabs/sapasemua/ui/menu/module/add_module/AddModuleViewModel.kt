package amirlabs.sapasemua.ui.menu.module.add_module

import amirlabs.sapasemua.base.DevViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable

class AddModuleViewModel  : DevViewModel(){
    private val disposable = CompositeDisposable()

    fun getModuleProgress(){

    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}