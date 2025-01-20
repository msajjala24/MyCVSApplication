package com.cvs.tagsnap.ui.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.cvs.tagsnap.R

@Composable
fun MyApp() {
    val navController = rememberNavController()

    // Set up navigation
    NavHost(navController = navController, startDestination = "search_screen") {
        composable("search_screen") {
            SharedScaffold(
                navController = navController,
                title = stringResource(R.string.app_name),
                content = {
                    SearchScreen(navController)
                })
        }
        composable(
            "detail_screen/{imageUrl}/{title}/{description}/{author}/{published}",
            arguments = listOf(
                navArgument("title") { type = NavType.StringType },
                navArgument("imageUrl") { type = NavType.StringType },
                navArgument("author") { type = NavType.StringType },
                navArgument("published") { type = NavType.StringType },
                navArgument("description") { type = NavType.StringType },
            )
        ) { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title") ?: ""
            val imageUrl = backStackEntry.arguments?.getString("imageUrl") ?: ""
            val author = backStackEntry.arguments?.getString("author") ?: ""
            val published = backStackEntry.arguments?.getString("published") ?: ""
            val description = backStackEntry.arguments?.getString("description") ?: ""
            SharedScaffold(
                navController = navController,
                title = title,
                content = {
                    DetailScreen(
                        title = title,
                        imageUrl = imageUrl,
                        author = author,
                        published = published,
                        description = description
                    )
                })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SharedScaffold(
    navController: NavController,
    title: String,
    content: @Composable () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
                navigationIcon = {
                    // Display a back arrow if there's a previous screen in the back stack
                    if (navController.previousBackStackEntry != null) {
                        // Back button (arrow)
                        androidx.compose.material3.IconButton(
                            onClick = { navController.popBackStack() }
                        ) {
                            androidx.compose.material3.Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = stringResource(R.string.back),
                                tint = MaterialTheme.colors.onPrimary
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colors.primary,
                    titleContentColor = Color.White
                )
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                content()
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun MyAppPreview() {
    MyApp()
}