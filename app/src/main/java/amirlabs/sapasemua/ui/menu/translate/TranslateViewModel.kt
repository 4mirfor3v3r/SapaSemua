package amirlabs.sapasemua.ui.menu.translate

import amirlabs.sapasemua.base.DevViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable

class TranslateViewModel : DevViewModel(){
    private val disposable = CompositeDisposable()
    fun getModuleProgress(){

    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }

}