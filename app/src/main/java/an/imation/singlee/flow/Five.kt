package an.imation.singlee.flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

class Five {

    fun main() = runBlocking {
        val time = measureTimeMillis {
            flow {
                repeat(3) {
                    delay(100) // Имитация медленного emit
                    emit(it)
                }
            }//.buffer()
                .collect {
                delay(300) // Медленный collect
                println("Collect: $it")
            }
        }
        println("Total time: $time ms")
    }

    /*fun main() = runBlocking {
        val numbersFlow = flow {
            for (i in 1..12) {
                delay(100)
                emit(i)
            }
        }

        numbersFlow
            .filter {
                it % 2 == 0
            }
            .map {
                "Число $it"
            }
            .drop(2)
            .take(2)
            .catch { e -> println("Ошибка: ${e.message}") }
            .collect { value ->
                println("Результат: $value")
            }
    }*/
}
/*flow { emit("A"); delay(100); emit("B"); delay(300); emit("C") }
.debounce(200) // Пропустит "A", но возьмёт "B" и "C"

flowOf(1, 2, 3).first { it > 1 } // 2

flow { if (Random.nextBoolean()) throw Error() else emit(1) }
.retry(3) //Попробует 3 раза

val flowA = flowOf("A", "B")
val flowB = flowOf(1, 2, 3)
flowA.combine(flowB) { a, b -> "$a$b" } // A1, B1, B2, B3
flowA.zip(flowB) { a, b -> "$a$b" } // A1, B2

flow {
    emit(1); delay(100); emit(2)
}.buffer().collect { delay(200); println(it) }// Эмиттер не ждёт 100мс после первого элемента

flowOf(1, 1, 2, 1).distinctUntilChanged() // 1, 2, 1

flowOf("a", "A", "b").distinctUntilChangedBy { it.lowercase() } // "a", "b"

flow { emit(1) }
.map { it * 2 } // Выполняется в IO
.flowOn(Dispatchers.IO)
.collect { }    // Выполняется в Default

flowOf(1, 2).onEach { println(it) } // 1, 2

flowOf(1, 2, 3, 4).scan(0) { acc, value -> acc + value }
// Результат: 0, 1, 3, 6, 10*/