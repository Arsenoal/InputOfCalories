package com.example.inputofcalories.domain.regularflow

import io.reactivex.Completable
import java.util.*

interface DeleteMealUseCase {
    fun delete(id: UUID): Completable
}