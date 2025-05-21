package an.imation.singlee

import an.imation.singlee.app.appModule
import an.imation.singlee.app.nestedGraphModule
import an.imation.singlee.app.networkModule
import an.imation.singlee.app.paginationModule
import an.imation.singlee.mock.IPostApiMock
import android.app.Application
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.GlobalContext.stopKoin
import org.koin.test.KoinTest
import org.mockito.Mockito

open class BaseTestClass : KoinTest {

    private val testDispatcher = TestCoroutineDispatcher()

    private val testModuleList = appModule + networkModule + paginationModule + nestedGraphModule + listOf(
        IPostApiMock().mModule()
    )


    @Before
    fun before() {
        startKoin {
            androidContext(Mockito.mock(Application::class.java))
            modules(testModuleList)
        }
        Dispatchers.setMain(testDispatcher)
    }


    @After
    fun after() {
        stopKoin()
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }


    fun test(
        invoke: suspend () -> Unit
    ) {
        runBlockingTest(testDispatcher) {
            invoke()
        }
    }

    fun <T> Flow<T>.parseEvent() = this.stateIn(MainScope(), SharingStarted.Eagerly, null)

}