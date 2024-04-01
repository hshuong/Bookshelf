package com.alexteddy.bookshelf.ui

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alexteddy.bookshelf.R
import com.alexteddy.bookshelf.ui.screens.BooksViewModel
import com.alexteddy.bookshelf.ui.screens.HomeScreen
import com.alexteddy.bookshelf.ui.screens.SearchBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookshelfApp() {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { BookshelfTopAppBar(scrollBehavior = scrollBehavior) }
    ) {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            val booksViewModel: BooksViewModel =
                viewModel(factory = BooksViewModel.Factory)
            HomeScreen(
                booksUiState = booksViewModel.booksUiState,
                retryAction = booksViewModel::getBooksInViewModel,
                userQuery = booksViewModel.userQuery,
                onSearchQueryChanged = {booksViewModel.updateQueryString(it)},
                onKeyboardDone = {booksViewModel.searchByQuery()},
                modifier = Modifier.fillMaxSize(),
                contentPadding = it,
            )
        }
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