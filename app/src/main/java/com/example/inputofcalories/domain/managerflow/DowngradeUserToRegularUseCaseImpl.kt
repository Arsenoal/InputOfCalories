package com.example.inputofcalories.domain.managerflow

import io.reactivex.Completable
import java.util.*

class DowngradeUserToRegularUseCaseImpl(
    private val downgradeUserToRegularUseCase: DowngradeUserToRegularUseCase
): DowngradeUserToRegularUseCase {

    override fun downgrade(userId: UUID): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}