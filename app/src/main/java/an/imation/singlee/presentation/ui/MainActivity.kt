package an.imation.singlee.presentation.ui

import MyTest
import an.imation.singlee.NavGraphs
import an.imation.singlee.flow.Five
import an.imation.singlee.presentation.ui.theme.SingleeTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialNavigationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyTest()
        enableEdgeToEdge()
        Five().main()
        setContent {
            SingleeTheme {
                Scaffold{padd->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padd)
                    ){
                        val navController = rememberAnimatedNavController()

                        DestinationsNavHost(
                            navGraph = NavGraphs.root,
                            navController = navController,
                            engine = rememberAnimatedNavHostEngine(
                                rootDefaultAnimations = RootNavGraphDefaultAnimations(
                                    enterTransition = { slideInHorizontally() + fadeIn() },
                                    exitTransition = { slideOutHorizontally() + fadeOut() },
                                    popEnterTransition = { slideInHorizontally(initialOffsetX = { -it }) + fadeIn() },
                                    popExitTransition = { slideOutHorizontally(targetOffsetX = { it }) + fadeOut() }
                                )
                            )
                        )
                    }
                }
            }
        }
    }
}