package com.example.inputofcalories.repo.launch

import io.reactivex.Completable

interface ClearDbRepo {
    fun clear(): Completable
}