package com.alexteddy.bookshelf.data

import com.alexteddy.bookshelf.network.BooksApiService
import com.alexteddy.bookshelf.network.SearchResult

interface BooksRepository {
    suspend fun searchBooks(
                    searchQuery: String = "jazz+history",
                    startIndex: String = "10",
                    maxResults: String = "40"
        ): SearchResult
}

class NetworkBooksRepository(
    private val booksApiService: BooksApiService
): BooksRepository {
    override suspend fun searchBooks(
        searchQuery: String,
        startIndex: String,
        maxResults: String
    ): SearchResult =
        booksApiService.getBooksService(
            searchQuery = searchQuery,
            startIndex = "0",
            maxResults = "40"
        )
}