package com.example.inputofcalories.repo.user

import com.example.inputofcalories.entity.register.*
import com.example.inputofcalories.repo.auth.registration.model.TYPE_ADMIN
import com.example.inputofcalories.repo.auth.registration.model.TYPE_MANAGER
import com.example.inputofcalories.repo.db.local.user.UserDao
import io.reactivex.Single

class GetUserFromLocalRepoImpl(
    private val userDao: UserDao
): GetUserRepo {
    override fun get(): Single<User> {
        return userDao.getUser().map { userRoom ->
            val type: UserType = when(userRoom.type) {
                TYPE_MANAGER -> { UserManager }
                TYPE_ADMIN -> { Admin }
                else -> { RegularUser }
            }

            val userParams = UserParams(
                name = userRoom.name,
                email = userRoom.email,
                type = type)

            val user = User(
                id = userRoom.id,
                userParams = userParams)

            user
        }
    }
}