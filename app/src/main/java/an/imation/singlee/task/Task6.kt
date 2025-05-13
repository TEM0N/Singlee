package an.imation.singlee.task

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.input.nestedscroll.NestedScrollSource.Companion.SideEffect
import kotlinx.coroutines.delay

@Composable
fun EffectOrderExample(key: Int) {
    println("--- Начало рекомпозиции ---")

    val value = remember(key) {
        println("remember: вычисление")
        "Value: $key"
    }

    SideEffect {
        println("SideEffect: выполнен")
    }

    DisposableEffect(key) {
        println("DisposableEffect: запущен")
        onDispose { println("DisposableEffect: отмена") }
    }

    LaunchedEffect(key) {
        println("LaunchedEffect: запущен")
        delay(1000)
        println("LaunchedEffect: завершён")
    }

    Text(text = value)
}