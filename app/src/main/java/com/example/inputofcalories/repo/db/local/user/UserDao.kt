package com.example.inputofcalories.repo.db.local.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Query("SELECT * FROM User")
    suspend fun getUser(): UserRoom

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userRoom: UserRoom)

    @Query("DELETE FROM User")
    suspend fun deleteUser()
}