package com.example.mvvm_kotlin.models.local

import com.example.mvvm_kotlin.models.responses.Post

interface ILocalDataSource {
    suspend fun shouldPreFetchPosts(): Boolean
    suspend fun fetchPosts(): List<Post>
    suspend fun updatePosts(list: List<Post>)
}

class LocalDataSource(
        private val database: PostDatabase
) : ILocalDataSource {

    override suspend fun shouldPreFetchPosts(): Boolean {
        return database.postDao().run {
            val postList = getAll()
            postList.isEmpty()
        }
    }

    override suspend fun fetchPosts(): List<Post> {
        return database.postDao().getAll()
    }

    override suspend fun updatePosts(list: List<Post>) {
        database.postDao().insertAll(*list.toTypedArray())
    }


}