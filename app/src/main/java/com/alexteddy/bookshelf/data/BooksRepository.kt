package com.alexteddy.bookshelf.data

import com.alexteddy.bookshelf.network.BooksApiService
import com.alexteddy.bookshelf.network.SearchResult

interface BooksRepository {
    suspend fun searchResult(): SearchResult
}

class NetworkBooksRepository(
    private val booksApiService: BooksApiService
): BooksRepository {
    override suspend fun searchResult(

    ): SearchResult =
        booksApiService.getBooksService(
            searchQuery = "jazz+history",
            startIndex = "0",
            maxResults = "40"
        )
}