package amirlabs.sapasemua.base

import amirlabs.sapasemua.data.api.NetworkConfig
import amirlabs.sapasemua.data.local.AppDatabase
import amirlabs.sapasemua.data.repo.MainRepositoryImpl
import amirlabs.sapasemua.ui.auth.login.LoginViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.reactivex.rxjava3.disposables.CompositeDisposable

class ViewModelFactory : ViewModelProvider.Factory {
    private val disposable = CompositeDisposable()
    private val db = AppDatabase.getAppDatabase()
    private val api = NetworkConfig.apiService
    private val mainRepo = MainRepositoryImpl(db.videoDao(), api)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(DevViewModel::class.java) -> DevViewModel() as T
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> LoginViewModel(mainRepo) as T
//            modelClass.isAssignableFrom(CCTVViewModel::class.java) -> CCTVViewModel(disposable, restRepository) as T
//            modelClass.isAssignableFrom(ReportViewModel::class.java) -> ReportViewModel(disposable, repository, restRepository) as T
//            modelClass.isAssignableFrom(SafeRouteViewModel::class.java) -> SafeRouteViewModel(disposable, repository, restRepository) as T
//            modelClass.isAssignableFrom(ListCCTVViewModel::class.java) -> ListCCTVViewModel(disposable, restRepository) as T
//            modelClass.isAssignableFrom(CCTVCameraViewModel::class.java) -> CCTVCameraViewModel(disposable, restRepository) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    companion object {
        @JvmStatic
        val viewModelFactory = ViewModelFactory()
    }
}