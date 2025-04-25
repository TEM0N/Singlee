package an.imation.singlee.flow

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.system.measureTimeMillis

class Seven {
/*
    viewModelScope.launch { // Род корутинa
        // Доч корутины
        launch { fetchUser() }
        launch { fetchPosts() }
    }
    */
    var counter = 0
    val mutex = Mutex()
    fun main() = runBlocking {
        repeat(100) {
          launch {
              //mutex.withLock {
                  val temp = counter
                  delay(1)
                  counter = temp + 1
              //}
         }
        }

        delay(500)
        println("Final counter: $counter")
    }
    /*
    flow {
    while (true) emit(System.currentTimeMillis()) // Очень быстрый поток
}.collect { value ->
    delay(1000) // Медлено обрабатываем
    println(value)
}
     */
}