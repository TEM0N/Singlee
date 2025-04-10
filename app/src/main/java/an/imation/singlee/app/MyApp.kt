package an.imation.singlee.app

import an.imation.singlee.data.api.IPostApi
import an.imation.singlee.data.api.RetrofitClient
import an.imation.singlee.data.mapper.PostDataMapper
import an.imation.singlee.data.repositoryImpl.LoginRepositoryImpl
import an.imation.singlee.data.repositoryImpl.PostsRepositoryImpl
import an.imation.singlee.data.repositoryImpl.TasksRepositoryImpl
import an.imation.singlee.domain.repository.ILoginRepository
import an.imation.singlee.domain.repository.IPostsRepository
import an.imation.singlee.domain.repository.ITasksRepository
import an.imation.singlee.domain.usecase.FetchPostsUseCase
import an.imation.singlee.domain.usecase.FetchTasksUseCase
import an.imation.singlee.domain.usecase.LoginUseCase
import an.imation.singlee.presentation.viewmodel.LoginVM
import an.imation.singlee.presentation.viewmodel.PostsVM
import an.imation.singlee.presentation.viewmodel.TasksVM
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
    single<ITasksRepository> { TasksRepositoryImpl() }
    single { FetchTasksUseCase(repository = get<ITasksRepository>()) }
    viewModel { TasksVM(fetchTasksUseCase = get<FetchTasksUseCase>()) }

    // Login feature
    single<ILoginRepository> { LoginRepositoryImpl() }
    single { LoginUseCase(repository = get<ILoginRepository>()) }
    viewModel { LoginVM(loginUseCase = get<LoginUseCase>()) }

    // Posts feature
    factory { PostDataMapper() }
    single { RetrofitClient.apiService }

    single<IPostsRepository> {
        PostsRepositoryImpl(
            apiService = get<IPostApi>(),
            mapper = get<PostDataMapper>()
        )
    }

    single {
        FetchPostsUseCase(
            repository = get<IPostsRepository>()
        )
    }

    viewModel {
        PostsVM(
            fetchPostsUseCase = get<FetchPostsUseCase>()
        )
    }
}