package amirlabs.sapasemua.base

import amirlabs.sapasemua.data.api.NetworkConfig
import amirlabs.sapasemua.data.local.AppDatabase
import amirlabs.sapasemua.data.repo.MainRepositoryImpl
import amirlabs.sapasemua.ui.auth.login.LoginViewModel
import amirlabs.sapasemua.ui.auth.register.RegisterViewModel
import amirlabs.sapasemua.ui.menu.home.HomeViewModel
import amirlabs.sapasemua.ui.menu.module.ModuleViewModel
import amirlabs.sapasemua.ui.menu.module.add_module.AddModuleViewModel
import amirlabs.sapasemua.ui.menu.module.add_quiz.AddQuizViewModel
import amirlabs.sapasemua.ui.menu.module.lesson.LessonViewModel
import amirlabs.sapasemua.ui.menu.module.list_quiz.ListQuizViewModel
import amirlabs.sapasemua.ui.menu.module.quiz.QuizViewModel
import amirlabs.sapasemua.ui.menu.module.submodule.SubModuleViewModel
import amirlabs.sapasemua.ui.menu.translate.TranslateViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class ViewModelFactory : ViewModelProvider.Factory {
    private val db = AppDatabase.getAppDatabase()
    private val api = NetworkConfig.apiService
    private val mainRepo = MainRepositoryImpl(db.videoDao(), api)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(DevViewModel::class.java) -> DevViewModel() as T
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> LoginViewModel(mainRepo) as T
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> RegisterViewModel(mainRepo) as T
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel() as T
            modelClass.isAssignableFrom(ModuleViewModel::class.java) -> ModuleViewModel(mainRepo) as T
            modelClass.isAssignableFrom(AddModuleViewModel::class.java) -> AddModuleViewModel(mainRepo) as T
            modelClass.isAssignableFrom(TranslateViewModel::class.java) -> TranslateViewModel(mainRepo) as T
            modelClass.isAssignableFrom(SubModuleViewModel::class.java) -> SubModuleViewModel(mainRepo) as T
            modelClass.isAssignableFrom(LessonViewModel::class.java) -> LessonViewModel(mainRepo) as T
            modelClass.isAssignableFrom(ListQuizViewModel::class.java) -> ListQuizViewModel(mainRepo) as T
            modelClass.isAssignableFrom(AddQuizViewModel::class.java) -> AddQuizViewModel(mainRepo) as T
            modelClass.isAssignableFrom(QuizViewModel::class.java) -> QuizViewModel(mainRepo) as T
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