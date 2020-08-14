package com.example.mvvm_kotlin.models

import com.example.mvvm_kotlin.common.Dispatchers
import com.example.mvvm_kotlin.models.local.ILocalDataSource
import com.example.mvvm_kotlin.models.remote.IRemoteDataSource
import com.example.mvvm_kotlin.models.responses.Post
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async

interface IRepository {
    suspend fun fetchPosts(): Deferred<List<Post>>
}

class Repository(
        private val dispatchers: Dispatchers,
        private val remoteDataSource: IRemoteDataSource,
        private val localDataSource: ILocalDataSource
) : IRepository {

    override suspend fun fetchPosts(): Deferred<List<Post>> {
        return runAsync {
            if (localDataSource.shouldPreFetchPosts()) {
                val postList = remoteDataSource.fetchPosts()
                updatePost(postList)
                postList
            } else localDataSource.fetchPosts()
        }
    }

    private suspend fun updatePost(postList: List<Post>) {
        localDataSource.updatePosts(postList)
    }

    /* -------------------------------------------------------------------------------------------*/
    /* Internal helpers */
    private fun <T> runAsync(task: suspend () -> T): Deferred<T> {
        return CoroutineScope(dispatchers.io()).async {
            task()
        }
    }

}