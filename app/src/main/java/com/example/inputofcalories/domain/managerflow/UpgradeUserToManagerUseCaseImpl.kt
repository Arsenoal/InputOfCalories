package com.example.inputofcalories.domain.managerflow

import com.example.inputofcalories.entity.manager.UserManagerStatusControlParams
import com.example.inputofcalories.repo.managerflow.UpgradeUserToManagerRepo
import io.reactivex.Completable
import java.util.*

class UpgradeUserToManagerUseCaseImpl(
    private val upgradeUserToManagerRepo: UpgradeUserToManagerRepo
): UpgradeUserToManagerUseCase {

    override fun upgrade(userId: UUID): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}