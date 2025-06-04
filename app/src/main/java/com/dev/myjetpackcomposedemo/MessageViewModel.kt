package com.dev.myjetpackcomposedemo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

// Repository class to handle data operations
class MessageRepository(private val dao: MessageDao) {
    fun getAllMessages() = dao.getAll()

    suspend fun insertMessage(message: MessageData) {
        dao.insert(message)
    }

    suspend fun deleteMessage(id : Int){
        dao.delete(id)
    }
}

// Database singleton
object DatabaseProvider {
    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(application: Application): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                application.applicationContext,
                AppDatabase::class.java,
                "message_database"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}

class MessageViewModel(app: Application) : AndroidViewModel(app) {
    private val repository = MessageRepository(
        DatabaseProvider.getDatabase(app).messageDao()
    )

    val allMessages: StateFlow<List<MessageData>> =
        repository.getAllMessages()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addMessage(contenu: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val message = MessageData(
                    pseudo = "NomPersonne",
                    contenuMessage = contenu,
                    idAvatar = R.drawable.doomguy
                )
                repository.insertMessage(message)
            } catch (e: Exception) {
                // Handle error (log, show toast, etc.)
                e.printStackTrace()
            }
        }
    }

    fun delete(idMessageToDelete : Int){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.deleteMessage(idMessageToDelete)
            }
            catch (e: Exception){
                e.printStackTrace()
            }
        }

    }
}