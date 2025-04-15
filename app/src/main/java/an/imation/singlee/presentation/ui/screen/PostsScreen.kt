package an.imation.singlee.presentation.ui.screen

import an.imation.singlee.R
import an.imation.singlee.domain.error.PostExceptionDomainModel
import an.imation.singlee.domain.model.PostDomainModel
import an.imation.singlee.presentation.source.NavigationUISource
import an.imation.singlee.presentation.event.posts.PostsIntent
import an.imation.singlee.presentation.event.posts.PostsState
import an.imation.singlee.presentation.ui.navigation.navigateToPostDetails
import an.imation.singlee.presentation.viewmodel.PostsViewModel
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel

fun NavController.navigateToPostsScreen() = navigate(
    NavigationUISource.POSTS_SCREEN)

@Composable
fun PostsScreen(navController: NavController) {
    val vm = koinViewModel<PostsViewModel>()
    val state: PostsState by vm.state.collectAsStateWithLifecycle()
    val intent: (PostsIntent) -> Unit by remember { mutableStateOf(vm::sendIntent) }

    PostsUI(
        state = state,
        intent = intent,
        onPostClick = { post -> navController.navigateToPostDetails(post) },
    )

    LaunchedEffect(Unit) {
        intent(PostsIntent.LoadPosts)
    }
}

@Composable
@Preview(showBackground = true)
private fun PostsUI(
    state: PostsState = PostsState(
        posts = listOf(
            PostDomainModel(1, 1, stringResource(R.string.preview_title_1), stringResource(R.string.preview_body_1)),
            PostDomainModel(1, 2, stringResource(R.string.preview_title_2), stringResource(R.string.preview_body_2))
        ),
        filteredPosts = listOf(
            PostDomainModel(1, 1, stringResource(R.string.preview_title_1), stringResource(R.string.preview_body_1)),
            PostDomainModel(1, 2, stringResource(R.string.preview_title_2), stringResource(R.string.preview_body_2))
        ),
        error = null,
        isLoading = false
    ),
    intent: (PostsIntent) -> Unit = {},
    onPostClick: (PostDomainModel) -> Unit = {},
) {
    Column(Modifier.fillMaxSize()) {
        if (!LocalInspectionMode.current) {
            SearchTextField(
                query = state.searchQuery,
                onQueryChange = { intent(PostsIntent.SearchPosts(it)) },
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        Box(Modifier.fillMaxSize()) {
            when {
                state.isLoading -> CustomLoader()
                state.error != null -> ErrorMessage(
                    error = state.error,
                    onRetry = { intent(PostsIntent.LoadPosts) }
                )
                else -> PostsList(
                    posts = if (LocalInspectionMode.current) state.posts else state.filteredPosts,
                    searchQuery = if (LocalInspectionMode.current) "" else state.searchQuery,
                    onPostClick = onPostClick
                )
            }
        }
    }
}


@Composable
private fun PostsList(
    posts: List<PostDomainModel>,
    searchQuery: String = "",
    onPostClick: (PostDomainModel) -> Unit = {}
) {
    LazyColumn(Modifier.fillMaxSize()) {
        items(posts) { post ->
            PostItem(
                post = post,
                searchQuery = searchQuery,
                onClick = onPostClick
            )
        }
    }
}

@Composable
@Preview
fun PostItem(
    post: PostDomainModel = PostDomainModel(1, 1,
        stringResource(R.string.sample_title),
        stringResource(R.string.sample_body)),
    searchQuery: String = "",
    modifier: Modifier = Modifier,
    onClick: (PostDomainModel) -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick(post) },
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            if (searchQuery.isNotEmpty()) {
                HighlightedText(
                    text = post.title,
                    searchQuery = searchQuery,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            } else {
                Text(
                    text = post.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (searchQuery.isNotEmpty()) {
                HighlightedText(
                    text = post.body,
                    searchQuery = searchQuery,
                    style = MaterialTheme.typography.bodyMedium
                )
            } else {
                Text(
                    text = post.body,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
private fun HighlightedText(
    text: String,
    searchQuery: String,
    style: TextStyle,
    fontWeight: FontWeight? = null
) {
    if (searchQuery.isEmpty() || searchQuery.isBlank()) {
        Text(
            text = text,
            style = style,
            fontWeight = fontWeight
        )
        return
    }

    val parts = text.split(searchQuery, ignoreCase = true)
    val highlightedColor = MaterialTheme.colorScheme.error

    Text(
        buildAnnotatedString {
            parts.forEachIndexed { index, part ->
                append(part)
                if (index != parts.lastIndex) {
                    withStyle(
                        style = SpanStyle(
                            background = highlightedColor,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append(text.substring(
                            text.indexOf(part, ignoreCase = true) + part.length,
                            text.indexOf(part, ignoreCase = true) + part.length + searchQuery.length
                        ))
                    }
                }
            }
        },
        style = style,
        fontWeight = fontWeight
    )
}

@Composable
@Preview(showBackground = true)
fun ErrorMessage(
    error: String? = null,
    modifier: Modifier = Modifier,
    onRetry: () -> Unit = {}
) {
    val context = LocalContext.current
    val displayError = error ?: context.getString(R.string.error_loading)

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = displayError,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onRetry,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(stringResource(R.string.retry_button))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ErrorMessage_ConnectError_Preview() {
    ErrorMessage(
        error = stringResource(R.string.error_no_internet),
        onRetry = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun ErrorMessage_LongError_Preview() {
    ErrorMessage(
        error = stringResource(R.string.error_long),
        onRetry = {}
    )
}