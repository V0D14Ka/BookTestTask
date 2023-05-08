package com.example.booktesttask.sources.books

import com.example.booktesttask.models.book.Book
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface BookApi {

    @GET("")
    suspend fun getRecommendations(): List<Book>

    @POST("{list}")
    suspend fun updatedGenres(@Path("list") list: List<String>)
    @GET("{id}")
    suspend fun getBook( @Path("id") id: Long): Book

    @GET("{id}")
    suspend fun like(@Path("id") id: Long) : Book

    @GET("{id}")
    suspend fun dislike(@Path("id") id: Long) : Book

    @GET("{id}")
    suspend fun alreadyRead(@Path("id") id: Long) : Book

    @GET("{id}")
    suspend fun skip(@Path("id") id: Long) : Book

}