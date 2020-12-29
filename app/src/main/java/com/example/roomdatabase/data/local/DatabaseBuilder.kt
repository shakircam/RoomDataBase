package com.example.roomdatabase.data.local

import android.content.Context
import androidx.room.Room
import com.example.roomdatabase.utils.DATABASE_NAME

object DatabaseBuilder {
    fun getInstance(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            DATABASE_NAME
        ).build()
    }
}