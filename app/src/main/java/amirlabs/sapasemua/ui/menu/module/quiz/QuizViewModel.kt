package amirlabs.sapasemua.ui.menu.module.quiz

import amirlabs.sapasemua.base.DevViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable

class QuizViewModel  : DevViewModel(){
    private val disposable = CompositeDisposable()

    fun getModuleProgress(){

    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}