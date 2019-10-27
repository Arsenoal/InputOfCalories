package com.example.inputofcalories.domain.launch

import io.reactivex.Completable

interface InvalidateLocalDataOnAppOpenUseCase {
    fun invalidate(): Completable
}