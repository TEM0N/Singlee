package an.imation.singlee.raamcosta

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class SharedViewModel : ViewModel() {
    private val _counter = MutableStateFlow(0)
    val counter = _counter.asStateFlow()

    init {
        viewModelScope.launch {
            while (isActive) {
                delay(1000)
                _counter.value++
                Log.d("SharedVM", "Counter: ${_counter.value}")
            }
        }
    }
}