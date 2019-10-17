package com.example.inputofcalories.domain.adminflow

import com.example.inputofcalories.repo.adminflow.DowngradeUserToManagerRepo
import io.reactivex.Completable
import java.util.*

class DowngradeUserToManagerUseCaseImpl(
    private val downgradeUserToManagerRepo: DowngradeUserToManagerRepo
): DowngradeUserToManagerUseCase {
    override fun downgrade(userId: UUID): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}