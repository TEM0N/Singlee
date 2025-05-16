package an.imation.singlee.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import kotlin.math.roundToInt

@Destination
@Composable
fun DraggableBoxScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var containerSize by remember { mutableStateOf(IntSize.Zero) }
        var redBoxSize by remember { mutableStateOf(IntSize.Zero) }
        Box(
            modifier = Modifier
                .width(200.dp)
                .height(250.dp)
                .background(Color.Black)
                .onGloballyPositioned { coordinates ->
                    containerSize = coordinates.size
                }
        ) {

            val initialOffset by remember(containerSize, redBoxSize) {
                derivedStateOf {
                    Offset(
                        (containerSize.width - redBoxSize.width) / 2f,
                        (containerSize.height - redBoxSize.height) / 2f
                    )
                }
            }

            var offset by remember { mutableStateOf(initialOffset) }

            LaunchedEffect(initialOffset) {
                offset = initialOffset
            }
            Box(
                modifier = Modifier
                    .width(45.dp)
                    .height(60.dp)
                    .onGloballyPositioned { coordinates ->
                        redBoxSize = coordinates.size
                    }
                    .offset {
                        IntOffset(offset.x.roundToInt(), offset.y.roundToInt())
                    }
                    .background(Color.Red)
                    .pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
                            change.consume()

                            val maxX = containerSize.width - redBoxSize.width
                            val maxY = containerSize.height - redBoxSize.height

                            offset = Offset(
                                (offset.x + dragAmount.x).coerceIn(0f, maxX.toFloat()),
                                (offset.y + dragAmount.y).coerceIn(0f, maxY.toFloat())
                            )
                        }
                    }
            )
        }
    }
}
