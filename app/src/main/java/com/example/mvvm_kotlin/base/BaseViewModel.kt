package com.example.mvvm_kotlin.base

import android.app.Application
import androidx.lifecycle.*
import com.example.mvvm_kotlin.common.postData
import com.example.mvvm_kotlin.models.Repository
import com.example.mvvm_kotlin.models.local.LocalDataSource
import com.example.mvvm_kotlin.models.local.PostDatabase
import com.example.mvvm_kotlin.models.remote.RemoteDataSource
import com.example.mvvm_kotlin.models.remote.RemoteDataSourceException
import com.example.mvvm_kotlin.models.remote.Service
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {

    //should use dagger
    //TODO
    val repository = Repository(com.example.mvvm_kotlin.common.Dispatchers(), RemoteDataSource(Service()), LocalDataSource(PostDatabase.invoke(application)))

    private val defaultCoroutineErrorHandler =
            CoroutineExceptionHandler(::handleDefaultCoroutineErrors)
    override val coroutineContext: CoroutineContext
        get() = viewModelScope.coroutineContext + Dispatchers.Main + defaultCoroutineErrorHandler

    private val _showErrorMessageEvent: MutableLiveData<String> = MutableLiveData()
    val showErrorMessageEvent: LiveData<String>
        get() = _showErrorMessageEvent

    /**
     * Coroutine Error handling
     * Could also include LocalDataSource
     */
    private fun handleDefaultCoroutineErrors(
            context: CoroutineContext,
            throwable: Throwable
    ) {
        when (throwable) {
            is RemoteDataSourceException -> handleRemoteDataSourceException(throwable)
            else -> handleUnknownException(throwable)
        }
    }

    /**
     * RemoteDataSource Error handling
     * Use exception.code to determine the error message
     * For this case, I'll just use an example error message
     */
    private fun handleRemoteDataSourceException(exception: RemoteDataSourceException) {
        showErrorMessage("Api Error")

    }

    protected open fun handleUnknownException(throwable: Throwable) {
        showErrorMessage("Unknown Error Occurred")
    }

    private fun showErrorMessage(message: String) {
        postData(_showErrorMessageEvent, message)
    }


}