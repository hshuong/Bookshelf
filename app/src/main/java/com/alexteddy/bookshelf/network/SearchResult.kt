package com.alexteddy.bookshelf.network

import com.google.gson.annotations.Expose
import kotlinx.serialization.Serializable

@Serializable
data class SearchResult(
    @Expose
    val kind: String,
    //val totalItems: Int,
    @Expose
    val items: List<Book>,
)

@Serializable
data class Book(
    @Expose
    val id: String,
    @Expose
    val volumeInfo: VolumeInfo,
)

@Serializable
data class VolumeInfo(
    @Expose
    val title: String,
    @Expose
    val authors: List<String>,
    @Expose
    val imageLinks: ImageLinks?
)

@Serializable
data class ImageLinks(
    @Expose
    val thumbnail: String?
)