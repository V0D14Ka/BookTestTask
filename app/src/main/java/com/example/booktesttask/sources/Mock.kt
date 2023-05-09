package com.example.booktesttask.sources

import android.util.Log
import com.example.booktesttask.models.book.Book
import com.example.booktesttask.models.book.BookRepository
import com.github.javafaker.Faker
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody

class Mock {
    private var books = mutableListOf<Book>()
    private var favorite_books = mutableListOf<String>()
    private var already_read_books = mutableListOf<String>()
    private val GENRES = mutableListOf(
        "Сказка",
        "Повесть",
        "Роман",
        "Детектив",
        "Фантастика"
    )
    private var genres = Array<Int>(GENRES.size){0}
    private var answer: String = """{"id":1, "username": "aboba", "favorite_books":[ """
    private val IMAGES = mutableListOf(
        "https://images.unsplash.com/photo-1543002588-bfa74002ed7e?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1887&q=80",
        "https://images.unsplash.com/photo-1576872381149-7847515ce5d8?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=736&q=80",
        "https://images.unsplash.com/photo-1544947950-fa07a98d237f?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=687&q=80",
        "https://images.unsplash.com/photo-1543497415-75c0a27177c0?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=627&q=80"
    )


    fun addFavoriteGenre(id: Long) {
        var current = 0
        GENRES.forEach {
            val g = books[id.toInt()-1].genre
            if (it == g) {
                genres[current] += 1
            }
            current+=1
        }
    }

    fun like(id: Long): String {
        favorite_books.add("+")
        addFavoriteGenre(id)
        return "+"
    }

    fun already(id: Long): String {
        already_read_books.add("+")
        addFavoriteGenre(id)
        return "+"
    }

    fun getAnswer() : String{
        makeAnswer()
        val res = answer
        answer = """{"id":1, "username": "aboba", "favorite_books":["""
        return res
    }
    private fun makeAnswer() {
        val sorted = mutableListOf<Pair<String, Int>>()

        favorite_books.forEach{
            answer += """"$it","""
        }
        answer = answer.removeSuffix(",")
        answer+= """],"already_read_books":[ """

        already_read_books.forEach{
            answer += """"$it","""
        }
        answer = answer.removeSuffix(",")
        answer+= """], "top": ["""

        var unique = 0
        genres.forEach {
            if (it != 0) {
                unique += 1
            }
        }

        var cur = 0
        genres.forEach {
            sorted.add(Pair(GENRES[cur], it))
            cur += 1
        }
        sorted.sortByDescending { it.second }

        when (unique) {
            0 -> answer += """"0"]}"""
            1 -> answer += """"${sorted[0].first}"]}"""
            2 -> answer += """"${sorted[0].first}", "${sorted[1].first}"]}"""
            else -> answer += """"${sorted[0].first}","${sorted[1].first}","${sorted[2].first}"]}"""
        }

    }

    fun offlineInit(): String {
        val faker = Faker.instance()
        IMAGES.shuffle()
        books = (1..20).map {
            Book(
                id = it.toLong(),
                author = faker.book().author(),
                price = 123,
                description = faker.lorem().characters(),
                name = faker.book().title(),
                genre = GENRES[it % GENRES.size],
                preview = IMAGES[it % IMAGES.size],
                isLiked = false,
                isRead = false
            )
        }.toMutableList()
        return Gson().toJson(books)
    }
}

class MockInterceptor : Interceptor {
    private val mock: Mock = Mock()

    override fun intercept(chain: Interceptor.Chain): Response {
        val uri = chain.request().url().uri().toString()
        val responseString = when {
            uri.endsWith("login") -> mock.getAnswer()
            uri.endsWith("dislike") -> "+"
            uri.endsWith("like") ->  {
                val id = uri.removeSuffix("/like").removePrefix("http://www.mocky.io/v2/").toLong()
                mock.like(id)}
            uri.endsWith("already") ->  {
                val id = uri.removeSuffix("/already").removePrefix("http://www.mocky.io/v2/").toLong()
                mock.already(id)}
            uri.endsWith("skip") ->  "+"
            uri.endsWith("getrecommendations") ->  mock.offlineInit()
            else  -> ""
        }
        return chain.proceed(chain.request())
            .newBuilder()
            .code(200)
            .protocol(Protocol.HTTP_2)
            .message(responseString)
            .body(
                ResponseBody.create(
                    MediaType.parse("application/json"),
                    responseString.toByteArray()))
            .addHeader("content-type", "application/json")
            .build()
    }
}
