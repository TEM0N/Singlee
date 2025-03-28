package an.imation.singlee.data

import an.imation.singlee.data.repositoryImpl.LoginRepositoryImpl
import an.imation.singlee.data.repositoryImpl.TasksRepositoryImpl
import an.imation.singlee.domain.repository.ILoginRepository
import an.imation.singlee.domain.repository.ITasksRepository
import an.imation.singlee.domain.usecase.FetchTasksUseCase
import an.imation.singlee.domain.usecase.LoginUseCase
import an.imation.singlee.presentation.viewmodel.LoginViewModel
import an.imation.singlee.presentation.viewmodel.TasksViewModel
import android.app.Application
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.logger.Level

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MyApplication)
            modules(appModule)
        }
    }
}
val appModule = module {
    single { FetchTasksUseCase(get()) }
    single<ITasksRepository> { TasksRepositoryImpl() }
    viewModel { TasksViewModel(get()) }
    single <ILoginRepository>{ LoginRepositoryImpl() }
    single { LoginUseCase(get()) }
    viewModel { LoginViewModel(get()) }
}