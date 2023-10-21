package amirlabs.sapasemua.ui.menu.forum

import amirlabs.sapasemua.base.DevViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable

class ForumViewModel  : DevViewModel(){
    private val disposable = CompositeDisposable()

    fun getModuleProgress(){

    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}