package com.example.inputofcalories.repo.registration.idcreator

import com.example.inputofcalories.repo.common.service.UUIDGeneratorService

class IdCreatorRepoImpl(
    private val generatorService: UUIDGeneratorService
): IdCreatorRepo {

    override fun get() = generatorService.get()
}