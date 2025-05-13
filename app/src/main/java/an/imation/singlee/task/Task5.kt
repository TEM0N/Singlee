package an.imation.singlee.task

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FlowRowExample() {
    val tags = listOf("Kotlin", "Compose", "Android", "Jetpack", "Material Design", "Coroutines", "Flow")

    FlowRow(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        maxItemsInEachRow = 4
    ) {
        tags.forEach { tag ->
            Box(
                modifier = Modifier
                    .border(1.dp, Color.Gray, RoundedCornerShape(16.dp))
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Text(tag)
            }
        }
    }
}

@Composable
fun AnimatedVisibilityExample() {
    var isVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { isVisible = !isVisible }) {
            Text(if (isVisible) "Скрыть" else "Показать")
        }

        Spacer(Modifier.height(16.dp))

        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(Color.Blue),
                contentAlignment = Alignment.Center
            ) {
                Text("Анимированный блок", color = Color.White)
            }
        }
    }
}
@Composable
fun FloatAnimationExample() {
    var isExpanded by remember { mutableStateOf(false) }
    val height by animateFloatAsState(
        targetValue = if (isExpanded) 200f else 100f,
        animationSpec = tween(durationMillis = 500), label = ""
    )

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { isExpanded = !isExpanded }) {
            Text(if (isExpanded) "Свернуть" else "Развернуть")
        }

        Spacer(Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(height.dp)
                .background(Color.Green)
        )
    }
}
@Composable
fun ColorAnimationExample() {
    var isRed by remember { mutableStateOf(true) }
    val color by animateColorAsState(
        targetValue = if (isRed) Color.Red else Color.Blue,
        animationSpec = tween(durationMillis = 1000), label = ""
    )

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { isRed = !isRed }) {
            Text("Изменить цвет")
        }

        Spacer(Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .size(150.dp)
                .background(color)
        )
    }
}
@Composable
fun DpAnimationExample() {
    var isWide by remember { mutableStateOf(false) }
    val width by animateDpAsState(
        targetValue = if (isWide) 300.dp else 150.dp,
        animationSpec = spring(dampingRatio = 0.5f), label = ""
    )

    Box(
        modifier = Modifier
            .width(width)
            .height(100.dp)
            .background(Color.Magenta)
            .clickable { isWide = !isWide }
    )
}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AnimatedListExample() {
    val items = remember { mutableStateListOf("A", "B", "C", "D") }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Button(onClick = { items.shuffle() }) {
            Text("Перемешать список")
        }

        LazyColumn {
            items(items, key = { it }) { item ->
                Text(
                    text = "Элемент $item",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .background(Color.LightGray)
                        .animateItemPlacement()
                )
            }
        }
    }
}
@SuppressLint("UnrememberedMutableState")
@Composable
fun DerivedStateExample() {
    var counter by remember { mutableStateOf(0) }
    val isEven by derivedStateOf {
        println("Вычисление isEven")
        counter % 2 == 0
    }

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { counter++ }) {
            Text("Увеличить счётчик: $counter")
        }
        Text("Чётное число: $isEven")
    }
}