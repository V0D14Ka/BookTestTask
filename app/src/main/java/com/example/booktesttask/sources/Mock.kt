package com.example.booktesttask.sources

import com.example.booktesttask.models.book.Book
import com.example.booktesttask.models.book.BookRepository
import com.github.javafaker.Faker
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody

class Mock {
    private var books = mutableListOf<Book>()
    private val id: Long = 1
    private val username: String = "aboba"
    private var favorite_books = mutableListOf<String>()
    private var already_read_books = mutableListOf<String>()
    private var answer: String = """{"id":1, "username": "aboba", "favorite_books":[ """
    private var answ: String = ""

    private val IMAGES = mutableListOf(
        "https://images.unsplash.com/photo-1543002588-bfa74002ed7e?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1887&q=80",
        "https://images.unsplash.com/photo-1576872381149-7847515ce5d8?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=736&q=80",
        "https://images.unsplash.com/photo-1544947950-fa07a98d237f?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=687&q=80",
        "https://images.unsplash.com/photo-1543497415-75c0a27177c0?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=627&q=80"
    )


    fun like(): String {
        favorite_books.add("+")
        return "+"
    }

    fun already(): String {
        already_read_books.add("+")
        return "+"
    }

    fun get_answer() : String{
        makeAnswer()
        val res = answer
        answer = """{"id":1, "username": "aboba", "favorite_books":["""
        return res
    }
    private fun makeAnswer() {
        favorite_books.forEach{
            answer += """""""
            answer += it
            answer += """""""
            answer += ","
        }
        answer = answer.removeSuffix(",")
        answer+= """],"already_read_books":[ """
        already_read_books.forEach{
            answer += """""""
            answer += it
            answer += """""""
            answer += ","
        }
        answer = answer.removeSuffix(",")
        answer+= """]}"""
    }

    fun offlineInit(): String  {
        val faker = Faker.instance()
        IMAGES.shuffle()
        books = (1..10).map {
            Book(
                id = it.toLong(),
                author = faker.book().author(),
                price = 123,
                description = faker.lorem().characters(),
                name = faker.book().title(),
                genre = faker.book().genre(),
                preview = IMAGES[it % IMAGES.size],
                isLiked = false,
                isRead = false
            )
        }.toMutableList()
        return "+"
    }
}

class MockInterceptor : Interceptor {
    private val mock: Mock = Mock()

    override fun intercept(chain: Interceptor.Chain): Response {
        val uri = chain.request().url().uri().toString()
        val responseString = when {
            uri.endsWith("login") -> mock.get_answer()
            uri.endsWith("dislike") -> "+"
            uri.endsWith("like") ->  mock.like()
            uri.endsWith("already") ->  mock.already()
            uri.endsWith("skip") ->  "+"
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
