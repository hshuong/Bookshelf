package com.alexteddy.bookshelf.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.alexteddy.bookshelf.BooksApplication
import com.alexteddy.bookshelf.data.BooksRepository
import com.alexteddy.bookshelf.network.SearchResult
import kotlinx.coroutines.launch
import java.io.IOException


sealed interface BooksUiState {
    data class Success(val searchResult: SearchResult) : BooksUiState
    // In order to store the data, add a constructor parameter to the Success data class
    data object Error : BooksUiState
    data object Loading : BooksUiState
}

class BooksViewModel(private val booksRepository: BooksRepository) : ViewModel() {
    var booksUiState: BooksUiState by mutableStateOf(BooksUiState.Loading)
        private set

    var userQuery by mutableStateOf("")
        private set
    init {
        getBooksInViewModel()
    }

    fun updateQueryString(query: String) {
        userQuery = query
    }

    fun searchByQuery() {
        if (userQuery.isNotEmpty()) {
            val userQueryPlus = userQuery.replace(' ','+')
            viewModelScope.launch {
                booksUiState = try {
                    BooksUiState.Success(
                        booksRepository.searchBooks(searchQuery = userQueryPlus)
                    )
                } catch (e: IOException) {
                    BooksUiState.Error
                }
            }
        }
    }

    fun getBooksInViewModel() {
        viewModelScope.launch {
            booksUiState = try {
                BooksUiState.Success(
                    booksRepository.searchBooks()
                )
            } catch (e: IOException) {
                BooksUiState.Error
            }
        }
    }
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BooksApplication)
                val booksRepository = application.container.booksRepository
                BooksViewModel(booksRepository = booksRepository)
            }
        }
    }

}
