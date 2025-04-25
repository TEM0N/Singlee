package an.imation.singlee.flow

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class Nine {
    /*fun updateUi(text: String) {
        CoroutineScope(Dispatchers.Main.immediate).launch {
            textView.text = text
        }
    }*/

    /*fun loadData() {
        CoroutineScope(Dispatchers.Unconfined).launch {
            //Начало в текущем потоке
            val data = fetchData() //Может переключиться на другой поток
            //Продолжение в потоке, где завершился fetchData()
            processData(data)
        }
    }*/
    fun demonstrateDispatcher() {
        runBlocking(Dispatchers.Unconfined) {
            println("Unconfined start: ${Thread.currentThread().name}")
            delay(100)
            println("Unconfined after delay: ${Thread.currentThread().name}")
        }
    }
}