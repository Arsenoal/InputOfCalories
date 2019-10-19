package com.example.inputofcalories.repo.common.service

import java.util.*

class UUIDGeneratorServiceImpl: UUIDGeneratorService {
    override fun get() = UUID.randomUUID()
}