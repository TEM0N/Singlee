package an.imation.singlee.task

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import kotlinx.coroutines.withContext
import java.util.UUID

class Task3 {
    private val scope = MainScope()

    fun startProcessing() {
        scope.launch {
            Log.e("!!!", "start")
            val result = fetchAllNews()
            Log.e("!!!", "${result.size}")
        }
    }
}
data class News(
    val id: String,
    val text: String
)

suspend fun getNewsByID(id: String): News = withContext(Dispatchers.IO) {
    delay(1000)
    return@withContext News(id = id, text = UUID.randomUUID().toString())
}

suspend fun getAllNewsIDs(): List<String> = withContext(Dispatchers.IO) {
    delay(2000)
    return@withContext (0 until 1000).map { it.toString() }
}

    suspend fun fetchAllNews(): List<News> = withContext(Dispatchers.IO) {
        val ids = getAllNewsIDs()
        val semaphore = Semaphore(50)
        return@withContext ids.map { id -> async { semaphore.withPermit { getNewsByID(id) }  } }
                .awaitAll()

}
