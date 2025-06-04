package com.dev.myjetpackcomposedemo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class MessageData(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val pseudo: String,
    val contenuMessage: String,
    val idAvatar: Int
)
