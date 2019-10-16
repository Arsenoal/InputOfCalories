package com.example.inputofcalories.common.rx

import io.reactivex.Scheduler

interface ThreadExecutor {
    val schedulerWorker: Scheduler
    val schedulerUi: Scheduler
}