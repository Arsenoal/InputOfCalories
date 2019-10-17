package com.example.inputofcalories.repo.common.service

import io.reactivex.Single
import java.util.*

interface UUIDGeneratorService {
    fun get(): Single<UUID>
}