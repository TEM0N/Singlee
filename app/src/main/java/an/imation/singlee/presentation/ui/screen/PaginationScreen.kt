package an.imation.singlee.presentation.ui.screen

import an.imation.singlee.presentation.event.pagination.PaginationEvent
import an.imation.singlee.presentation.event.pagination.PaginationIntent
import an.imation.singlee.presentation.event.pagination.PaginationState
import an.imation.singlee.presentation.viewmodel.PaginationViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import org.koin.androidx.compose.koinViewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination


@Destination
@Composable
fun PaginationScreen() {
    val vm = koinViewModel<PaginationViewModel>()
    val state: PaginationState by vm.state.collectAsStateWithLifecycle()
    val intent: (PaginationIntent) -> Unit by remember { mutableStateOf(vm::sendIntent) }
    val event: Flow<PaginationEvent> by remember { mutableStateOf(vm.event) }

    PaginationUI(
        state = state,
        intent = intent,
        vm = vm
    )

    LaunchedEffect(Unit) {
        intent(PaginationIntent.LoadFirstPage)
    }

    LaunchedEffect(event) {
        event.filterIsInstance<PaginationEvent.ShowError>().collect { error ->
            // Мб уведомление об ошибке
        }
    }
}

@Composable
@Preview
private fun PaginationUI(
    state: PaginationState = PaginationState(
        items = (0..10).map { "1-$it" } + (0..10).map { "2-$it" },
        currentPage = 2,
        maxPage = 5,
        isAllDataLoaded = false
    ),
    intent: (PaginationIntent) -> Unit = {},
    vm: PaginationViewModel? = null
) {
    val listState = rememberLazyListState()

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -1 }
            .collect { lastIndex ->
                vm?.handleScroll(lastIndex)
            }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize()
        ) {
            items(state.items, key = { it }) { item ->
                Text(
                    text = item,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 60.dp)
                        .background(Color.Green)
                        .padding(24.dp),
                    color = Color.White,
                    fontSize = 24.sp
                )
            }

            item {
                when {

                    state.isLoading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                    state.isAllDataLoaded -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("All data loaded")
                        }
                    }
                    state.error != null -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("Error: ${state.error}")
                            }
                        }
                    }
                }
            }
        }
    }
}