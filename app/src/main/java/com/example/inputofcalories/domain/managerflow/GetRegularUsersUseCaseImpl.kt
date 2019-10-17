package com.example.inputofcalories.domain.managerflow

import com.example.inputofcalories.entity.User
import com.example.inputofcalories.repo.managerflow.GetRegularUsersRepo
import io.reactivex.Single

class GetRegularUsersUseCaseImpl(
    private val getRegularUsersRepo: GetRegularUsersRepo
): GetRegularUsersUseCase {

    override fun get(): Single<List<User>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}