package com.example.mvvm_kotlin.common

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface IDispatchers {
    fun io(): CoroutineDispatcher
    fun ui(): CoroutineDispatcher
}

class Dispatchers : IDispatchers {

    override fun io(): CoroutineDispatcher = Dispatchers.IO

    override fun ui(): CoroutineDispatcher = Dispatchers.Main
}