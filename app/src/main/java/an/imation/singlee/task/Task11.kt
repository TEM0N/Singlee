package an.imation.singlee.task

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import kotlinx.coroutines.withContext

class Task11 {
    private val mScope = MainScope() + Dispatchers.IO

    fun startProcessing() {
        val job = mScope.launch {
            (0..100).forEach {
                val result =  test(it)
                Log.e("!!!", "$result")
            }
        }

        mScope.launch {
            delay(3_500)
            job.cancel()
            Log.e("!!!", "отмена")
        }
    }

    private suspend fun test(id: Int): String = withContext(Dispatchers.IO) {
        Thread.sleep(1000)
        return@withContext id.toString()
    }
}