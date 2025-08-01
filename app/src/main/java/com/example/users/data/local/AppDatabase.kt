package com.example.users.data.local

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [User::class], version = 1, exportSchema = false)
abstract  class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
}