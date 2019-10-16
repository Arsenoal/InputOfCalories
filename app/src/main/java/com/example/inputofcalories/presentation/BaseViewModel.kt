package com.example.inputofcalories.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.inputofcalories.common.mapper.Mapper
import com.example.inputofcalories.common.rx.HandleError
import com.example.inputofcalories.common.rx.RxExecutor
import com.example.inputofcalories.common.rx.Success
import com.example.inputofcalories.common.rx.SuccessCompletable
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import org.koin.core.KoinComponent
import org.koin.core.inject

open class BaseViewModel : ViewModel(), KoinComponent {

    private val rxExecutor: RxExecutor by inject()

    fun execute(completable: Completable,
                requestCode: Int? = null,
                handleError: HandleError = this::handleError,
                success: SuccessCompletable? = null) {
        rxExecutor.run(completable, requestCode = requestCode, handleError = handleError, success = success)
    }

    fun <T> execute(single: Single<T>,
                    requestCode: Int? = null,
                    handleError: HandleError = this::handleError,
                    success: Success<T>? = null) {
        rxExecutor.run(single, requestCode = requestCode, handleError = handleError, success = success)
    }

    fun <T> execute(observable: Observable<T>,
                    requestCode: Int? = null,
                    handleError: HandleError = this::handleError,
                    success: Success<T>? = null) {
        rxExecutor.run(observable, requestCode = requestCode, handleError = handleError, success = success)
    }

    fun <T> executeAndPost(single: Single<T>,
                           liveData: MutableLiveData<T>,
                           requestCode: Int? = null,
                           handleError: HandleError = this::handleError) {
        rxExecutor.run(single, requestCode = requestCode, handleError = handleError) {
            liveData.postValue(it)
        }
    }

    fun <T, R> executeMap(observable: Observable<T>,
                          mapper: Mapper<T, R>,
                          requestCode: Int? = null,
                          handleError: HandleError = this::handleError,
                          success: Success<R>? = null) {
        val map = observable.map {
            mapper.map(it)
        }
        execute(map, requestCode = requestCode, handleError = handleError, success = success)
    }


    fun <T, R> executeMapAndPost(single: Single<T>,
                                 liveData: MutableLiveData<R>,
                                 mapper: Mapper<T, R>,
                                 requestCode: Int? = null,
                                 handleError: HandleError = this::handleError) {
        val map = single.map {
            mapper.map(it)
        }
        execute(map, requestCode = requestCode, handleError = handleError) {
            liveData.postValue(it)
        }
    }

    fun <T, R> executeMapAndPost(observable: Observable<T>,
                                 liveData: MutableLiveData<R>,
                                 mapper: Mapper<T, R>,
                                 requestCode: Int? = null,
                                 handleError: HandleError = this::handleError) {
        val map = observable.map {
            mapper.map(it)
        }
        execute(map, requestCode = requestCode, handleError = handleError) {
            liveData.postValue(it)
        }
    }

    fun <T, R> executeMapListAndPost(single: Single<List<T>>,
                                     liveData: MutableLiveData<List<R>>,
                                     mapper: Mapper<T, R>,
                                     requestCode: Int? = null,
                                     handleError: HandleError = this::handleError) {
        val map = single.map {
            mapper.map(it)
        }
        execute(map, requestCode = requestCode, handleError = handleError) {
            liveData.postValue(it)
        }
    }

    fun <T, R> executeMapListAndPost(observable: Observable<List<T>>,
                                     liveData: MutableLiveData<List<R>>,
                                     mapper: Mapper<T, R>,
                                     requestCode: Int? = null,
                                     handleError: HandleError = this::handleError) {
        val map = observable.map {
            mapper.map(it)
        }
        execute(map, requestCode = requestCode, handleError = handleError) {
            liveData.postValue(it)
        }
    }


    open fun handleError(throwable: Throwable, requestCode: Int?) {
        //TODO add log
    }


    override fun onCleared() {
        rxExecutor.clearAll()
        super.onCleared()
    }
}