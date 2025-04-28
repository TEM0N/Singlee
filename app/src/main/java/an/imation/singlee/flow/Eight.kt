package an.imation.singlee.flow

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter

class Eight {
    fun calculate(a: Int, b: Int, operation: (Int, Int) -> Int): Int {
        return operation(a, b)
    }

    fun getMultiplier(factor: Int): (Int) -> Int {
        return { number -> number * factor }
    }
    suspend fun main(){
        val sum = calculate(5, 3) { x, y -> x + y }
        val doubler = getMultiplier(2)
        println(sum)
        println(doubler(5))

        val isString = checkType<String>("Hello")
        val isInt = checkType<Int>(123)
        println(isInt)
    }
    inline fun <reified T> checkType(obj: Any): Boolean {
        return obj is T
    }
    /*fun <T> checkType(obj: Any): Boolean {
        return obj is T  //Cannot check for instance of erased type
    }*/


}

/*fun <T> Flow<T>.filterWithLog(predicate: (T) -> Boolean): Flow<T> = filter {
    println("Filtering: $it")
    predicate(it)
}

inline fun <T> Flow<T>.filterWithLog(crossinline predicate: (T) -> Boolean): Flow<T> = filter {
    println("Filtering: $it")
    predicate(it)
}

viewModelScope.launch {
    userFlow
        .filterWithLog { it.age > 18 }
        .collect { users -> updateUi(users) }
}*/