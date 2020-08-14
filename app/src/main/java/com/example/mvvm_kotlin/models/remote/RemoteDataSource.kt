package com.example.mvvm_kotlin.models.remote

import com.example.mvvm_kotlin.models.responses.Post

interface IRemoteDataSource {
    suspend fun fetchPosts(): List<Post>
}

class RemoteDataSource(
        private val service: Service
) : IRemoteDataSource {
    override suspend fun fetchPosts(): List<Post> {
        return runOrThrow {
            service.fetchPosts()
        }
    }

    /* -------------------------------------------------------------------------------------------*/
    /* Internal helpers */
    private inline fun <reified T> runOrThrow(task: () -> T): T {
        try {
            return task()
        } catch (e: Exception) {
            throw RemoteDataSourceException.of(e)
        }
    }

}
