package com.alexteddy.bookshelf.network

import retrofit2.http.GET
import retrofit2.http.Query

interface BooksApiService {
    //@GET("volumes?q=mystery&startIndex=10&maxResults=40")
    @GET("volumes")
    suspend fun getBooksService(
        @Query("q") searchQuery: String,
        @Query("startIndex") startIndex: String,
        @Query("maxResults") maxResults: String
    ): SearchResult
}