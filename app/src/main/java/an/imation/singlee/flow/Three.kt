package an.imation.singlee.flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class Three {
    fun main() = runBlocking {
        val sharedFlow = MutableSharedFlow<Int>(3)

        launch {
            repeat(5) {
                sharedFlow.emit(it)  // 0, 1, 2, 3, 4
                delay(500)
            }
        }

        delay(1000)
        launch {
            sharedFlow.collect { value ->
                println("Подписчик 1: $value")
            }
        }

        delay(1000)
        launch {
            sharedFlow.collect { value ->
                println("Подписчик 2: $value")
            }
        }

        delay(3000)
    }

   /* fun main() = runBlocking {

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
    }*/
}