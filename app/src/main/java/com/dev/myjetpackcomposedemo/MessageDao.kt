package com.dev.myjetpackcomposedemo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {
    @Query("SELECT * FROM messages ORDER BY id DESC")
    fun getAll(): Flow<List<MessageData>>

    @Insert
    suspend fun insert(msg: MessageData)
}
