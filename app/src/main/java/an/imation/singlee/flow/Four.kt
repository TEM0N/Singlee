package an.imation.singlee.flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class Four {
    /*fun main() = runBlocking {
        val coldFlow = flow {
            println("Начинаем эмиссию")
            emit(1)
            delay(1000)
            emit(2)
            delay(1000)
            emit(3)
        }

        val coroutineScope = CoroutineScope(Dispatchers.IO)

        val hotStateFlow = coldFlow.stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(), // Автоматически останавливается при отсутствии подписчиков
            initialValue = 0
        )

        val job1 = launch {
            hotStateFlow.collect { value ->
                println("Подписчик 1: $value")
            }
        }

        delay(1500)

        val job2 = launch {
            hotStateFlow.collect { value ->
                println("Подписчик 2: $value")
            }
        }

        delay(2000)
        job1.cancel()
        job2.cancel()
        coroutineScope.cancel()
    }*/

    fun main() = runBlocking {
        val coldFlow = flow {
            println("Начинаем эмиссию")
            emit(1)
            delay(1000)
            emit(2)
            delay(1000)
            emit(3)
        }

        val coroutineScope = CoroutineScope(Dispatchers.IO)

        val hotSharedFlow = coldFlow.shareIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(),
            replay = 0
        )

        val job1 = launch {
            hotSharedFlow.collect { value ->
                println("Подписчик 1: $value")
            }
        }

        delay(1000)

        val job2 = launch {
            hotSharedFlow.collect { value ->
                println("Подписчик 2: $value")
            }
        }

        delay(2000)
        job1.cancel()
        job2.cancel()
        coroutineScope.cancel()
    }
}