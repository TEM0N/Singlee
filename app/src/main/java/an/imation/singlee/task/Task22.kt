package an.imation.singlee.task

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

class Task22 {
    suspend fun test(a: Int): Unit = withContext(Dispatchers.IO) {
        delay(a * 1000L)
        if (Random.nextBoolean()) throw Exception("error - ${a}")
        Log.e("!!!", "${a}")
    }


    fun startProcessing() {
        val job = MainScope().launch(Dispatchers.IO) {
            (0..10).forEach {
                launch {
                    runCatching {
                        test(it)
                    }.onFailure { e ->
                        ensureActive()
                        Log.e("!!!", "Ошибка: ${e.stackTraceToString()}")
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