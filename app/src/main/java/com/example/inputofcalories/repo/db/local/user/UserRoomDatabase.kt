package com.example.inputofcalories.repo.db.local.user

import androidx.room.Database
import androidx.room.RoomDatabase
import android.content.Context
import androidx.room.Room
import com.example.inputofcalories.repo.db.local.user.UserDbInfo.DB_NAME
import com.example.inputofcalories.repo.db.local.user.UserDbInfo.DB_VERSION

@Database(entities = [UserRoom::class], version = DB_VERSION)
abstract class UserRoomDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        @Volatile private var INSTANCE: UserRoomDatabase? = null

        fun getInstance(context: Context): UserRoomDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room
                .databaseBuilder(context.applicationContext, UserRoomDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }
}