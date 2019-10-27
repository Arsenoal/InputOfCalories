package com.example.inputofcalories.repo.launch

import com.example.inputofcalories.repo.db.local.user.UserDao

class ClearDbRepoImpl(
    private val userDao: UserDao
): ClearDbRepo {
    override fun clear() = userDao.deleteUser()
}