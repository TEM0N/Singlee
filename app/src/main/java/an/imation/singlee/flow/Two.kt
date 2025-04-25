package an.imation.singlee.flow

import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

class Two {
    fun fetchNumbers(): Flow<Int> = flow {
        for (i in 1..3) {
            delay(500)
            emit(i)
        }
    }

    fun main() = runBlocking {
        fetchNumbers()
            .collect { number -> println("Получено: $number") }
    }

    /*fun realParallelFlow(): Flow<Int> = channelFlow {
        launch {
            delay(Random.nextLong(10, 100))
            send(1)
        }
        launch {
            delay(Random.nextLong(10, 100))
            send(2)
        }
    }

    fun main() = runBlocking {
        repeat(5) {
            realParallelFlow().collect { println(it) }
            println("---")
        }
    }*/
    /*fun tickerFlow(): Flow<Int> = callbackFlow {
        var count = 0
        val timer = java.util.Timer()

        // Колбэк будет вызываться каждые 500 мс
        timer.schedule(object : java.util.TimerTask() {
            override fun run() {
                trySend(count++)  // Отправляем значение в Flow
            }
        }, 0, 500)

        awaitClose { timer.cancel() }
    }

    fun main() = runBlocking {
        tickerFlow().collect { println("Tick: $it") }
    }*/
}