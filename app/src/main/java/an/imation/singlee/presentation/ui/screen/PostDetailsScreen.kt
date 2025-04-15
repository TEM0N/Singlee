package an.imation.singlee.presentation.ui.screen

import an.imation.singlee.R
import an.imation.singlee.domain.model.CommentDomainModel
import an.imation.singlee.domain.model.PostDomainModel
import an.imation.singlee.presentation.event.comments.PostDetailsEvent
import an.imation.singlee.presentation.event.comments.PostDetailsIntent
import an.imation.singlee.presentation.event.comments.PostDetailsState
import an.imation.singlee.presentation.source.NavigationUISource
import an.imation.singlee.presentation.viewmodel.PostDetailsViewModel
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.Flow
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

fun getPostDetailsScreenName() = NavigationUISource.POST_DETAILS

@Composable
fun PostDetailsScreen(post: PostDomainModel) {
    val vm = koinViewModel<PostDetailsViewModel>(parameters = { parametersOf(post) })
    val state: PostDetailsState by vm.state.collectAsStateWithLifecycle()
    val intent: (PostDetailsIntent) -> Unit by remember { mutableStateOf(vm::sendIntent) }
    val event: Flow<PostDetailsEvent> by remember { mutableStateOf(vm.event) }

    PostDetailsUI(
        state = state,
        intent = intent
    )

    LaunchedEffect(Unit) {
        intent(PostDetailsIntent.LoadComments)
    }
}
@Composable
@Preview(showBackground = true)
private fun PostDetailsUI(
    state: PostDetailsState = PostDetailsState(
        post = PostDomainModel(1, 1, "", ""),
        comments = listOf(
            CommentDomainModel(1, 1, "", "", ""),
            CommentDomainModel(1, 2, "", "", "")
        ),
        isLoading = false,
        error = null
    ),
    intent: (PostDetailsIntent) -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        PostItem(
            post = state.post,
            modifier = Modifier.padding(8.dp)
        )

        Divider(
            modifier = Modifier.padding(vertical = 8.dp),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.outlineVariant
        )

        Text(
            text = stringResource(R.string.comments_title, state.comments.size),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        when {
            state.isLoading -> {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CustomLoader()
                }
            }
            state.error != null -> {
                ErrorMessage(
                    error = state.error,
                    onRetry = { intent(PostDetailsIntent.LoadComments) },
                    modifier = Modifier.padding(16.dp)
                )
            }
            else -> {
                LazyColumn {
                    items(state.comments) { comment ->
                        CommentItem(comment = comment)
                    }
                }
            }
        }
    }
}

@Composable
private fun CommentItem(comment: CommentDomainModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
        )
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = comment.name,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = comment.email,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = comment.body,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}