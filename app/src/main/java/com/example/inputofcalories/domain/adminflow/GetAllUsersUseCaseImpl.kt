package com.example.inputofcalories.domain.adminflow

import com.example.inputofcalories.entity.User
import com.example.inputofcalories.repo.adminflow.GetAllUsersRepo
import io.reactivex.Single

class GetAllUsersUseCaseImpl(
    private val getAllUsersRepo: GetAllUsersRepo
): GetAllUsersUseCase {
    override fun get(): Single<List<User>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}