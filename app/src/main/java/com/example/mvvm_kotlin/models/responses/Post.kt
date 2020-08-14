package com.example.mvvm_kotlin.models.responses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Post(

        @ColumnInfo(name = "title")
        @SerializedName("title")
        @PrimaryKey
        val postTitle: String,

        @ColumnInfo(name = "image")
        @SerializedName("image")
        val postLink: String?,


        @ColumnInfo(name = "filter")
        @SerializedName("filter")
        val postFilter: String?

)