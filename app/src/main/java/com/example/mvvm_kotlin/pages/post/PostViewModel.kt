package com.example.mvvm_kotlin.pages.post

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvm_kotlin.base.BaseViewModel
import com.example.mvvm_kotlin.common.postData
import com.example.mvvm_kotlin.models.responses.Post
import kotlinx.coroutines.launch

class PostViewModel(application: Application) : BaseViewModel(application) {

    private lateinit var posts: List<Post>

    private val _postList = MutableLiveData<List<Post>>()
    val postList: LiveData<List<Post>>
        get() = _postList

    private val _imageFilters = MutableLiveData<String?>()
    val imageFilters: LiveData<String?>
        get() = _imageFilters

    private val _emptyResponse = MutableLiveData<Boolean>()
    val emptyResponse: LiveData<Boolean>
        get() = _emptyResponse


    /*--------------------------------------------------------------------------------------------*/
    /* Public function */
    fun start() {
        if (!_postList.value.isNullOrEmpty()) {
            return
        } else {
            startInternally()
        }
    }

    private fun startInternally() {

        launch {
            posts = fetchPosts()

            if (posts.isNullOrEmpty()) {
                postData(_emptyResponse, true)
            } else {
                postData(_postList, posts)
            }
        }
    }

    fun showFilter(position: Int) {
        val post = posts[position]
        postData(_imageFilters, post.postFilter)
    }


    /*--------------------------------------------------------------------------------------------*/
    /* Api */
    private suspend fun fetchPosts(): List<Post> {
        return repository.fetchPosts().await()
    }

}