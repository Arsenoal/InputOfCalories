package com.example.inputofcalories.repo.launch

import com.example.inputofcalories.repo.db.local.user.UserDao

class ClearDbRoom(
    private val userDao: UserDao
): ClearDbRepo {
    override suspend fun clear() = userDao.deleteUser()
}