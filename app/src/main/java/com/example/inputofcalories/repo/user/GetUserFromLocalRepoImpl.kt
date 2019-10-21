package com.example.inputofcalories.repo.user

import com.example.inputofcalories.entity.register.User
import com.example.inputofcalories.entity.register.UserParams
import com.example.inputofcalories.repo.db.local.user.UserDao
import io.reactivex.Single

class GetUserFromLocalRepoImpl(
    private val userDao: UserDao
): GetUserRepo {
    override fun get(): Single<User> {
        return userDao.getUser().map { userRoom ->
            val userParams = UserParams(
                name = userRoom.name,
                email = userRoom.email)

            val user = User(
                id = userRoom.id,
                userParams = userParams)

            user
        }
    }
}