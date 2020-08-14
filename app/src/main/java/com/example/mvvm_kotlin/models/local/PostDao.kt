package com.example.mvvm_kotlin.models.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.mvvm_kotlin.models.responses.Post

@Dao
interface PostDao {

    @Insert
    suspend fun insertAll(vararg post: Post): List<Long>

    @Query("SELECT * FROM post")
    suspend fun getAll(): List<Post>

}