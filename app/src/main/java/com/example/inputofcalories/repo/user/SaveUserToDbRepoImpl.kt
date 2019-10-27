package com.example.inputofcalories.repo.user

import com.example.inputofcalories.entity.register.Admin
import com.example.inputofcalories.entity.register.RegularUser
import com.example.inputofcalories.entity.register.User
import com.example.inputofcalories.entity.register.UserManager
import com.example.inputofcalories.repo.auth.registration.model.TYPE_ADMIN
import com.example.inputofcalories.repo.auth.registration.model.TYPE_MANAGER
import com.example.inputofcalories.repo.auth.registration.model.TYPE_REGULAR
import com.example.inputofcalories.repo.db.local.user.UserDao
import com.example.inputofcalories.repo.db.local.user.UserRoom
import io.reactivex.Completable
import io.reactivex.Single

class SaveUserToDbRepoImpl(
    private val userDao: UserDao
): SaveUserToDbRepo {
    override fun save(user: User): Completable {
        return Single.create<UserRoom> {
            val type: Int = when(user.userParams.type) {
                RegularUser -> { TYPE_REGULAR }
                UserManager -> { TYPE_MANAGER }
                Admin -> { TYPE_ADMIN }
            }

            val roomUser = UserRoom(
                id = user.id,
                name = user.userParams.name,
                email = user.userParams.email,
                dailyCalories = user.userParams.dailyCalories,
                type = type)

            it.onSuccess(roomUser)
        }.flatMapCompletable {
            userDao.insertUser(it)
        }
    }
}