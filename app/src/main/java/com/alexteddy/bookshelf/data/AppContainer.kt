package com.alexteddy.bookshelf.data

import com.alexteddy.bookshelf.network.BooksApiService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val booksRepository: BooksRepository
}

class DefaultAppContainer : AppContainer {
    private val baseUrl =
        //"https://android-kotlin-fun-mars-server.appspot.com"
        "https://www.googleapis.com/books/v1/"

    private val gson: Gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()

    private val retrofit = Retrofit.Builder() // Dung bo tao doi tuong Retrofit
        //.addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl) // ket noi den web service nao
        .build() // Tao doi tuong Retrofit

    private val retrofitService : BooksApiService by lazy {
        retrofit.create(BooksApiService::class.java)
    }

    override val booksRepository: BooksRepository by lazy {
        NetworkBooksRepository(retrofitService)
    }
}