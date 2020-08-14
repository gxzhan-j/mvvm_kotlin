package com.example.mvvm_kotlin.common

import android.util.Log

private typealias ThrowableBuilder = (String, Throwable?) -> Throwable
private typealias ThrowableReporter = (Throwable) -> Unit

/**
 * A wrapped Log class.
 */
object Logger {
    private const val TAG = "AppLogger"

    var debugLevel = Log.VERBOSE
    var throwableReporter: ThrowableReporter? = null

    private val defaultThrowableBuilder: ThrowableBuilder = { msg, e ->
        RuntimeException(msg, e)
    }

    fun v(msg: String, tag: String? = null, e: Throwable? = null) {
        log(msg, tag, e, Log.VERBOSE)
    }

    fun d(msg: String, tag: String? = null, e: Throwable? = null) {
        log(msg, tag, e, Log.DEBUG)
    }

    fun i(msg: String, tag: String? = null, e: Throwable? = null) {
        log(msg, tag, e, Log.INFO)
    }

    fun w(msg: String, tag: String? = null, e: Throwable? = null) {
        log(msg, tag, e, Log.WARN)
    }

    fun e(msg: String, tag: String? = null, e: Throwable? = null) {
        log(msg, tag, e, Log.ERROR)
    }

    fun report(
            msg: String,
            tag: String? = null,
            e: Throwable? = null,
            buildThrowable: ThrowableBuilder = defaultThrowableBuilder
    ) {
        e(msg, tag, e)

        val message = buildMessage(msg, tag)
        val customException = buildThrowable(message, e)

        throwableReporter?.invoke(customException)
    }

    private fun log(msg: String, tag: String?, e: Throwable?, level: Int) {
        if (debugLevel > level) {
            return
        }

        val message = buildMessage(msg, tag)
        when (level) {
            Log.VERBOSE -> if (e == null) Log.v(TAG, message) else Log.v(TAG, message, e)
            Log.DEBUG -> if (e == null) Log.d(TAG, message) else Log.d(TAG, message, e)
            Log.INFO -> if (e == null) Log.i(TAG, message) else Log.i(TAG, message, e)
            Log.WARN -> if (e == null) Log.w(TAG, message) else Log.w(TAG, message, e)
            Log.ERROR -> if (e == null) Log.e(TAG, message) else Log.e(TAG, message, e)
        }
    }

    private fun buildMessage(msg: String, tag: String?): String {
        return if (tag.isNullOrBlank()) msg else "[$tag] $msg"
    }
}