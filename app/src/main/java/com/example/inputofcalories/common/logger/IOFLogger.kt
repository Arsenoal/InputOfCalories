package com.example.inputofcalories.common.logger

import timber.log.Timber

object IOFLogger {
    fun init() {
        Timber.plant(Timber.DebugTree())
    }

    fun v(tag: String, message: String, vararg args: Any?, t: Throwable? = null) { Timber.tag(tag).v(t, message, args) }

    fun d(tag: String, message: String?, vararg args: Any?, t: Throwable? = null) { Timber.tag(tag).d(t, message, args) }

    fun i(tag: String, message: String?, vararg args: Any?, t: Throwable? = null) { Timber.tag(tag).i(t, message, args) }

    fun w(tag: String, message: String?, vararg args: Any?, t: Throwable? = null) { Timber.tag(tag).w(t, message, args) }

    fun e(tag: String, message: String?, vararg args: Any?, t: Throwable? = null) { Timber.tag(tag).e(t, message, args) }

    fun wtf(tag: String, message: String?, vararg args: Any?, t: Throwable? = null) { Timber.tag(tag).wtf(t, message, args) }
}