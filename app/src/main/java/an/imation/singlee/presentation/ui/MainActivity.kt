package an.imation.singlee.presentation.ui

import an.imation.singlee.flow.Four
import an.imation.singlee.flow.Nine
import an.imation.singlee.flow.One
import an.imation.singlee.flow.Seven
import an.imation.singlee.flow.Six
import an.imation.singlee.flow.Three
import an.imation.singlee.presentation.ui.navigation.AppNavHost
import an.imation.singlee.presentation.viewmodel.TasksViewModel
import an.imation.singlee.presentation.ui.theme.SingleeTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.getViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        Nine().demonstrateDispatcher()
        setContent {
            SingleeTheme {
                Scaffold{padd->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padd)
                    ){
                        val navController = rememberNavController()
                        val tasksViewModel: TasksViewModel = getViewModel()

                        AppNavHost(navController, tasksViewModel)
                    }
                }
            }

        }
    }
}