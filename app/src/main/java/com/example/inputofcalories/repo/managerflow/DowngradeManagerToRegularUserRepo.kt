package com.example.inputofcalories.repo.managerflow

import io.reactivex.Completable
import java.util.*

interface DowngradeManagerToRegularUserRepo {
    fun downgrade(userId: String): Completable
}