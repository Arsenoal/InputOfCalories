package com.example.inputofcalories.domain.adminflow

import io.reactivex.Completable
import java.util.*

class UpgradeUserToAdminUseCaseImpl(
    private val upgradeUserToAdminUseCase: UpgradeUserToAdminUseCase
): UpgradeUserToAdminUseCase {
    override fun upgrade(userId: UUID): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}