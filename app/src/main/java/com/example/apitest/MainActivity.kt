package com.example.apitest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.apitest.presentation.mainscreen.view.MainScreen
import com.example.apitest.presentation.mainscreen.MainScreenViewModel
import com.example.apitest.ui.theme.APITESTTheme
import com.example.apitest.utils.Route
import dagger.hilt.android.AndroidEntryPoint

//TODO
// 1.Clean Architecture
// 2.Повторить SOLID, DRY, KISS
// 3. Аналитика, файрбейз и тд
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainScreenViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            APITESTTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Route.MainScreen.route
                ) {
                    composable(route = Route.MainScreen.route) {
                        MainScreen(viewModel)
                    }
                }
            }
        }
    }
}