package an.imation.singlee.task

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext
import kotlin.random.Random

class Task21 {

    suspend fun test(a: Int): Unit = withContext(Dispatchers.IO) {
        delay(a * 1000L)
        if (Random.nextBoolean()) throw Exception("error - ${a}")
        Log.e("!!!", "${a}")
    }


    fun startProcessing() {
        val exceptionHandler = CoroutineExceptionHandler { _, exception ->
            Log.e("!!!", "Ошибка: ${exception.message}")
        }

        val job = MainScope().launch(Dispatchers.IO + exceptionHandler) {
            supervisorScope {
                (0..10).forEach {
                    launch {
                        test(it)
                    }
                }
            }
        }
        MainScope().launch(Dispatchers.IO) {
            delay(3_500)
            job.cancel()
            Log.e("!!!", "отмена")
        }
    }
}