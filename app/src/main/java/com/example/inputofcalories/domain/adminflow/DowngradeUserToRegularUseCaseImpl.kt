package com.example.inputofcalories.domain.adminflow

import com.example.inputofcalories.repo.adminflow.DowngradeUserToRegularRepo
import io.reactivex.Completable
import java.util.*

class DowngradeUserToRegularUseCaseImpl(
    private val downgradeUserToRegularRepo: DowngradeUserToRegularRepo
): DowngradeUserToRegularUseCase {
    override fun downgrade(userId: UUID): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}