package com.example.inputofcalories.common.rx

import com.example.inputofcalories.common.exception.handleCrashedExceptionOrRunBlock
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.util.*
import java.util.concurrent.ConcurrentHashMap

typealias HandleError = (t: Throwable, requestCode: Int?) -> Unit
typealias SuccessCompletable = () -> Unit
typealias Success<T> = (T) -> Unit

class RxExecutor(private val threadExecutor: ThreadExecutor) {

    private val groupDisposables: MutableMap<UUID, CompositeDisposable> = ConcurrentHashMap()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun run(completable: Completable,
            groupId: UUID? = null,
            requestCode: Int? = null,
            handleError: HandleError? = null,
            success: SuccessCompletable? = null) {
        val disposable = completable
            .subscribeOn(threadExecutor.schedulerWorker)
            .observeOn(threadExecutor.schedulerUi)
            .subscribe({
                success?.invoke()
            }, {
                it.printStackTrace()
                handleCrashedExceptionOrRunBlock(it) {
                    handleError?.invoke(it, requestCode)
                }
            })

        if (groupId == null) compositeDisposable.add(disposable)
        else addDisposableByGroupId(groupId, disposable)
    }

    fun <T> run(single: Single<T>,
                groupId: UUID? = null,
                requestCode: Int? = null,
                handleError: HandleError? = null,
                success: Success<T>? = null) {
        val disposable = single
            .subscribeOn(threadExecutor.schedulerWorker)
            .observeOn(threadExecutor.schedulerUi)
            .subscribe({
                success?.invoke(it)
            }, {
                it.printStackTrace()
                handleCrashedExceptionOrRunBlock(it) {
                    handleError?.invoke(it, requestCode)
                }
            })

        if (groupId == null) compositeDisposable.add(disposable)
        else addDisposableByGroupId(groupId, disposable)
    }

    fun <T> run(observable: Observable<T>,
                groupId: UUID? = null,
                requestCode: Int? = null,
                handleError: HandleError? = null,
                success: Success<T>? = null) {
        val disposable = observable
            .subscribeOn(threadExecutor.schedulerWorker)
            .observeOn(threadExecutor.schedulerUi)
            .subscribe({
                success?.invoke(it)
            }, {
                it.printStackTrace()
                handleCrashedExceptionOrRunBlock(it) {
                    handleError?.invoke(it, requestCode)
                }
            })

        if (groupId == null) compositeDisposable.add(disposable)
        else addDisposableByGroupId(groupId, disposable)
    }

    fun clearAll() {
        compositeDisposable.clear()
    }

    private fun addDisposableByGroupId(groupId: UUID, disposable: Disposable) {
        if (!groupDisposables.containsKey(groupId)) groupDisposables[groupId] = CompositeDisposable()

        groupDisposables[groupId]?.add(disposable)
    }

    fun clearDisposablesByGroupId(groupId: UUID) {
        groupDisposables.remove(groupId)?.clear()
    }

    fun clearAllDisposableGroups() {
        groupDisposables.forEach { it.value.clear() }
        groupDisposables.clear()
    }
}