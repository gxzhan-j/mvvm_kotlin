package com.example.mvvm_kotlin.models.remote

import retrofit2.HttpException
import java.net.ConnectException


class RemoteDataSourceException @JvmOverloads constructor(throwable: Throwable? = null) : Exception(throwable) {

    /**
     * Include different exception codes
     * Making handle errors easier
     * Following are some examples
     */
    companion object {
        const val ERROR_SERVER = 500
        const val ERROR_NO_NETWORK = 501
        const val ERROR_UNKNOWN = 502


        fun of(throwable: Throwable): RemoteDataSourceException {
            return when (throwable) {
                is RemoteDataSourceException -> throwable
                is HttpException -> RemoteDataSourceException(throwable)
                is ConnectException -> RemoteDataSourceException(
                        throwable
                )
                else -> {
                    RemoteDataSourceException(throwable)
                }
            }
        }
    }


}