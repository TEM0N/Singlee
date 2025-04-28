package an.imation.singlee.flow

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class Three {
    /*fun main() = runBlocking {
        val sharedFlow = MutableSharedFlow<Int>(
            replay = 1,
            extraBufferCapacity = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        )

        launch {
            repeat(5) {
                sharedFlow.emit(it)
                delay(50)
            }
        }

        delay(100)
        sharedFlow.collect { println("Получено: $it") }
        delay(3000)
    }*/

    fun main() = runBlocking {

        val stateFlow = MutableStateFlow(0)

        launch {
            repeat(5) {
                stateFlow.emit(it)
                delay(500)
            }
        }
        launch {
            stateFlow.collect { value ->
                println("Подписчик 1: $value")
            }
        }

        delay(1500)
        launch {
            stateFlow.collect { value ->
                println("Подписчик 2: $value")
            }
        }

        delay(1000)
    }
}