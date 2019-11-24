package com.example.inputofcalories.repo.user

import com.example.inputofcalories.entity.register.*
import com.example.inputofcalories.repo.auth.registration.model.TYPE_ADMIN
import com.example.inputofcalories.repo.auth.registration.model.TYPE_MANAGER
import com.example.inputofcalories.repo.db.local.user.UserDao
import io.reactivex.Single
import kotlinx.coroutines.suspendCancellableCoroutine

class GetUserFromRoom(
    private val userDao: UserDao
): GetUserRepo {
    override suspend fun get(): User {

        val userRoom = userDao.getUser()
        val userParams = userRoom.run {
            val type: UserType = when (type) {
                TYPE_MANAGER -> { UserManager }
                TYPE_ADMIN -> { Admin }
                else -> { RegularUser }
            }

            UserParams(
                name = name,
                email = email,
                dailyCalories = dailyCalories,
                type = type
            )
        }

        return User(id = userRoom.id, userParams = userParams)
    }
}