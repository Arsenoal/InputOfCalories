package com.example.inputofcalories.common.rx

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ThreadExecutorImpl : ThreadExecutor {
    override val schedulerWorker: Scheduler
        get() = Schedulers.io()
    override val schedulerUi: Scheduler
        get() =  AndroidSchedulers.mainThread()

}