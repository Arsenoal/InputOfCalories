package com.example.inputofcalories.repo.common.service

import io.reactivex.Single
import java.util.*

class UUIDGeneratorServiceImpl: UUIDGeneratorService {
    override fun get(): Single<UUID> {
        return Single.fromCallable {
            UUID.randomUUID()
        }
    }
}