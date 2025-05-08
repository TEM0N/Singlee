package an.imation.singlee.app

import an.imation.singlee.MyOkHttpClient
import an.imation.singlee.OkhttpCache.setOkhttpCache
import an.imation.singlee.data.api.IPostApi
import an.imation.singlee.data.mapper.CommentDataMapper
import an.imation.singlee.data.mapper.PostDataMapper
import an.imation.singlee.data.repositoryImpl.CommentsRepositoryImpl
import an.imation.singlee.data.repositoryImpl.LoginRepositoryImpl
import an.imation.singlee.data.repositoryImpl.PostsRepositoryImpl
import an.imation.singlee.data.repositoryImpl.TasksRepositoryImpl
import an.imation.singlee.domain.model.PostDomainModel
import an.imation.singlee.domain.repository.ICommentsRepository
import an.imation.singlee.domain.repository.ILoginRepository
import an.imation.singlee.domain.repository.IPostsRepository
import an.imation.singlee.domain.repository.ITasksRepository
import an.imation.singlee.domain.usecase.FetchCommentsUseCase
import an.imation.singlee.domain.usecase.FetchPostsUseCase
import an.imation.singlee.domain.usecase.FetchTasksUseCase
import an.imation.singlee.domain.usecase.LoginUseCase
import an.imation.singlee.presentation.viewmodel.LoginViewModel
import an.imation.singlee.presentation.viewmodel.PaginationViewModel
import an.imation.singlee.presentation.viewmodel.PostDetailsViewModel
import an.imation.singlee.presentation.viewmodel.PostsViewModel
import an.imation.singlee.presentation.viewmodel.TasksViewModel
import android.app.Application
import org.koin.android.ext.koin.androidApplication
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.logger.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MyApplication)
            modules(listOf(appModule, networkModule, paginationModule))
        }
    }
}
val appModule = module {
    single<ITasksRepository> { TasksRepositoryImpl() }
    factory<FetchTasksUseCase> { FetchTasksUseCase(repository = get<ITasksRepository>()) }
    viewModel<TasksViewModel> { TasksViewModel(fetchTasksUseCase = get<FetchTasksUseCase>()) }

    // Login feature
    single<ILoginRepository> { LoginRepositoryImpl() }
    factory<LoginUseCase> { LoginUseCase(repository = get<ILoginRepository>()) }
    viewModel<LoginViewModel> { LoginViewModel(loginUseCase = get<LoginUseCase>()) }

    // Posts feature
    factory<PostDataMapper> { PostDataMapper() }

    single<IPostsRepository> {
        PostsRepositoryImpl(
            apiService = get<IPostApi>(),
            mapper = get<PostDataMapper>(),
        )
    }

    factory<FetchPostsUseCase> {
        FetchPostsUseCase(
            repository = get<IPostsRepository>()
        )
    }

    viewModel<PostsViewModel> {
        PostsViewModel(
            fetchPostsUseCase = get<FetchPostsUseCase>()
        )
    }

    // Comments feature
    factory<CommentDataMapper> { CommentDataMapper() }
    single<ICommentsRepository> {
        CommentsRepositoryImpl(
            apiService = get<IPostApi>(),
            mapper = get<CommentDataMapper>()
        )
    }
    factory<FetchCommentsUseCase> { FetchCommentsUseCase(repository = get<ICommentsRepository>()) }

    viewModel { (post: PostDomainModel) ->
        PostDetailsViewModel(
            post = post,
            fetchCommentsUseCase = get<FetchCommentsUseCase>()
        )
    }

}

val paginationModule = module {
    viewModel { PaginationViewModel() }
}
val networkModule = module {

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(MyOkHttpClient().get())
            .build()
            .setOkhttpCache(androidApplication())
    }

    single<IPostApi> { get<Retrofit>().create(IPostApi::class.java) }
}