package an.imation.singlee.flow

import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.random.Random

class One{
    suspend fun fetchUserData(): String = suspendCancellableCoroutine { continuation ->
        //  Имитируем асинхронный callback-запрос
        val callback = object {
            fun onSuccess(data: String) = continuation.resume(data)
            fun onError(error: Throwable) = continuation.resumeWithException(error)
        }
        // Запускаем запрос
        Thread {
            Thread.sleep(1000) // Имитируем задержки сети
            if (Random.nextBoolean()) {
                callback.onSuccess("Данные пользователя")
            } else {
                callback.onError(RuntimeException("Ошибка сервера"))
            }
        }.start()
        // Обработчик отмены сработает при cancel()
        continuation.invokeOnCancellation {
            println("Операция отменена!")
        }
    }
    fun main() = runBlocking {
        val job = launch {
            try {
                val data = fetchUserData()
                println("Успех: $data")
            } catch (e: Exception) {
                println("Ошибка: ${e.message}")
            }
        }
        delay(500)
        job.cancel()
    }

}
