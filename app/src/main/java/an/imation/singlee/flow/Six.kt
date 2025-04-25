package an.imation.singlee.flow

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class Six {

    fun main() = runBlocking {
        val startTime = System.currentTimeMillis()

        flowOf(1, 2, 3)
            .flatMapConcat { id ->
                flow {
                    delay(1000)
                    emit("Data $id")
                }
            }
            .collect { data ->
                println("$data at ${System.currentTimeMillis() - startTime}ms")
            }
    }
    /*fun main() = runBlocking {
        val searchQuery = flowOf("a", "ab", "abc")

        searchQuery
            .flatMapLatest { query ->
                flow {
                    delay(1000)
                    emit("Results for $query")
                }
            }
            .collect { results ->
                println(results)
            }
    }*/
}