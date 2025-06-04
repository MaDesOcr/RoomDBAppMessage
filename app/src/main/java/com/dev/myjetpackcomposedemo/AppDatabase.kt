package com.dev.myjetpackcomposedemo

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MessageData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao
}
