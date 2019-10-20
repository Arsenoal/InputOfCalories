package com.example.inputofcalories.repo.user

import com.example.inputofcalories.entity.register.User
import com.example.inputofcalories.repo.db.local.user.UserDao
import com.example.inputofcalories.repo.db.local.user.UserRoom
import io.reactivex.Completable
import io.reactivex.Single

class SaveUserToDbRepoImpl(
    private val userDao: UserDao
): SaveUserToDbRepo {
    override fun save(user: User): Completable {
        return Single.create<UserRoom> {
            val roomUser = UserRoom(
                id = user.id ?: "",
                name = user.userParams.name,
                email = user.userParams.email
            )

            it.onSuccess(roomUser)
        }.flatMapCompletable {
            userDao.insertUser(it)
        }
    }
}