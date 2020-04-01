package com.example.inputofcalories.repo.user

import com.example.inputofcalories.entity.register.*
import com.example.inputofcalories.repo.auth.model.TYPE_ADMIN
import com.example.inputofcalories.repo.auth.model.TYPE_MANAGER
import com.example.inputofcalories.repo.auth.model.TYPE_REGULAR
import com.example.inputofcalories.repo.db.local.user.UserDao
import com.example.inputofcalories.repo.db.local.user.UserRoom

class UserRoomRepo(
    private val userDao: UserDao
): UserRepo {
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

    override suspend fun set(user: User) {
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