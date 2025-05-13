package an.imation.singlee.task

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
@Composable
fun MyView(
    modifier: Modifier = Modifier,
    item: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .border(2.dp, Color.White)
            .padding(2.dp)
            .width(IntrinsicSize.Min)
    ) {
        item()
    }
}
@Composable
@Preview
private fun Preview() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Green)
    ) {
        MyView(
            item = {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Red),
                    text = "text",
                )
            }
        )
        Text("базовый")

        MyView(
            modifier = Modifier
                .fillMaxWidth(),
            item = {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Yellow),
                    text = "text",
                )
            }
        )
        Text("на всю ширину")

        Row {
            MyView(
                modifier = Modifier
                    .weight(1f),
                item = {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Red),
                        text = "1111",
                    )
                }
            )
            MyView(
                modifier = Modifier
                    .weight(1f),
                item = {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Yellow),
                        text = "222",
                    )
                }
            )
        }
        Text("2 вью на всю ширину экрана")
    }
}
