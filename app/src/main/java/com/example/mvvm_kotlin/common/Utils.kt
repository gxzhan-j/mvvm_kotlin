package com.example.mvvm_kotlin.common

import android.os.Looper
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

/*--------------------------------------------------------------------------------------------*/
/* ViewModel */
fun <T> Fragment.observe(liveData: LiveData<T>?, handler: (T) -> Unit) {
    liveData?.observe(viewLifecycleOwner, Observer {
        handler(it)
    })
}

/*--------------------------------------------------------------------------------------------*/
/* LiveData */
fun <T> postData(liveData: MutableLiveData<T>, data: T) {
    if (Thread.currentThread() == Looper.getMainLooper().thread) {
        liveData.value = data
    } else {
        liveData.postValue(data)
    }
}