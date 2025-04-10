package an.imation.singlee.presentation.ui.navigation

import an.imation.singlee.presentation.ui.screen.navigateToLoginScreen
import an.imation.singlee.presentation.ui.screen.navigateToPostsScreen
import androidx.navigation.NavController

object TaskIds {
    const val LOGIN_TASK = 1
    const val POSTS_TASK = 2
}
fun NavController.handleTaskClick(taskId: Int) {
    when (taskId) {
        TaskIds.POSTS_TASK -> navigateToPostsScreen()
        TaskIds.LOGIN_TASK -> navigateToLoginScreen()
        else -> navigateToLoginScreen()
    }
}