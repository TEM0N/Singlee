package an.imation.singlee.presentation.ui.navigation

import an.imation.singlee.domain.model.PostDomainModel
import an.imation.singlee.presentation.ui.screen.navigateToLoginScreen
import an.imation.singlee.presentation.ui.screen.navigateToPaginationScreen
import an.imation.singlee.presentation.ui.screen.navigateToPostsScreen
import android.net.Uri
import androidx.navigation.NavController
import com.google.gson.Gson

object TaskIds {
    const val LOGIN_TASK = 1
    const val POSTS_TASK = 2
    const val PAGINATION_TASK = 3

}
fun NavController.handleTaskClick(taskId: Int) {
    when (taskId) {
        TaskIds.POSTS_TASK -> navigateToPostsScreen()
        TaskIds.LOGIN_TASK -> navigateToLoginScreen()
        TaskIds.PAGINATION_TASK -> navigateToPaginationScreen()
        else -> navigateToLoginScreen()
    }
}

fun NavController.navigateToPostDetails(post: PostDomainModel) {
    val postJson = Uri.encode(Gson().toJson(post))
    navigate("postDetails/$postJson")
}