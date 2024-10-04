package com.lemieux.movielist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.ui.Modifier
import com.lemieux.detail.MovieDetailsScreen
import com.lemieux.feed.MovieListScreen
import com.lemieux.movielist.ui.theme.MovieListTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3AdaptiveApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieListTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navigator = rememberListDetailPaneScaffoldNavigator<Int>()
                    val lazyListState = rememberLazyListState()

                    BackHandler(navigator.canNavigateBack()) {
                        navigator.navigateBack()
                    }

                    ListDetailPaneScaffold(
                        directive = navigator.scaffoldDirective,
                        value = navigator.scaffoldValue,
                        listPane = {
                            AnimatedPane {
                                MovieListScreen(
                                    lazyListState = lazyListState,
                                    onClickItem = {
                                        navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, it)
                                    })
                            }
                        },
                        detailPane = {
                            AnimatedPane {
                                navigator.currentDestination?.content?.let {
                                    MovieDetailsScreen(
                                        it,
                                    )
                                }
                            }
                        },
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }
}