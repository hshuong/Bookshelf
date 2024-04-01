package com.alexteddy.bookshelf.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.alexteddy.bookshelf.R
import com.alexteddy.bookshelf.ui.screens.BooksViewModel
import com.alexteddy.bookshelf.ui.screens.HomeScreen
import com.alexteddy.bookshelf.ui.screens.SearchBar

enum class BookScreen(@StringRes val title: Int) {
    BookList(title = R.string.app_name),
    BookDetail(title = R.string.book_detail),
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookshelfApp(
        booksViewModel: BooksViewModel = viewModel(factory = BooksViewModel.Factory),
        navController: NavHostController = rememberNavController()) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { BookshelfTopAppBar(scrollBehavior = scrollBehavior) }
    ) {
        NavHost(
            navController = navController, // de navigate qua lai giua cac screen
            startDestination = BookScreen.BookList.name,
            modifier = Modifier.padding(it) // it PaddingValues from Scaffold
        ) {
                composable(BookScreen.BookList.name) {
                    HomeScreen(
                        booksUiState = booksViewModel.booksUiState,
                        retryAction = booksViewModel::getBooksInViewModel,
                        userQuery = booksViewModel.userQuery,
                        onSearchQueryChanged = {booksViewModel.updateQueryString(it)},
                        onKeyboardDone = {booksViewModel.searchByQuery()},
                        modifier = Modifier.fillMaxSize(),
                        //contentPadding = it,
                    )
                }


//                composable(CupcakeScreen.Start.name) {
//                    StartOrderScreen(
//                        quantityOptions = DataSource.quantityOptions,
//                        onNextButtonClicked = {//ab ->
//                            navController.navigate(CupcakeScreen.Flavor.name)
//                        },
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .padding(dimensionResource(R.dimen.padding_medium))
//                    )
//                }
//                composable(route = CupcakeScreen.Flavor.name) {
//                    val context = LocalContext.current
//                    SelectOptionScreen(
//                        subtotal = uiState.price, // lay tong gia tri don hang tu uiState
//                        onNextButtonClicked = { navController.navigate(CupcakeScreen.Pickup.name) },
//                        onCancelButtonClicked = {cancelOrderAndNavigateToStart(viewModel, navController)},
//                        options = DataSource.flavors.map { id -> context.resources.getString(id) },
//                        onSelectionChanged = { viewModel.setFlavor(it) },
//                        modifier = Modifier.fillMaxHeight()
//                    )
//                }
        }

//        Surface(
//            modifier = Modifier.fillMaxSize()
//        ) {
//            val booksViewModel: BooksViewModel =
//                viewModel(factory = BooksViewModel.Factory)
//            HomeScreen(
//                booksUiState = booksViewModel.booksUiState,
//                retryAction = booksViewModel::getBooksInViewModel,
//                userQuery = booksViewModel.userQuery,
//                onSearchQueryChanged = {booksViewModel.updateQueryString(it)},
//                onKeyboardDone = {booksViewModel.searchByQuery()},
//                modifier = Modifier.fillMaxSize(),
//                //contentPadding = it,
//            )
        //}
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookshelfTopAppBar(scrollBehavior: TopAppBarScrollBehavior, modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineMedium,
            )
        },
        modifier = modifier
    )
}