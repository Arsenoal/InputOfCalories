package com.example.inputofcalories.repo.db.local.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface UserDao {

    @Query("SELECT * FROM User")
    fun getUser(): Single<UserRoom>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(userRoom: UserRoom): Completable

    @Query("DELETE FROM User")
    fun deleteUser(): Completable
}