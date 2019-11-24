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

class SaveUserToRoom(
    private val userDao: UserDao
): SaveUserToDbRepo {
    override suspend fun save(user: User) {
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

        userDao.insertUser(roomUser)
    }
}