package com.example.inputofcalories.repo.managerflow

import io.reactivex.Completable
import java.util.*

interface DowngradeManagerUserRepo {
    fun downgrade(userId: String): Completable
}