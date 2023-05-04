package com.example.booktesttask.models.book;

import com.example.booktesttask.models.Repository


typealias BookListener = (posts: List<Book>) -> Unit
typealias LikedBookListener = (likedposts: List<Book>) -> Unit

class BookRepository : Repository {
}
