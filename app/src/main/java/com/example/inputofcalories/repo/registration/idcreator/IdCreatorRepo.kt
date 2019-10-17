package com.example.inputofcalories.repo.registration.idcreator

import io.reactivex.Single
import java.util.*

interface IdCreatorRepo {
    fun get(): Single<UUID>
}