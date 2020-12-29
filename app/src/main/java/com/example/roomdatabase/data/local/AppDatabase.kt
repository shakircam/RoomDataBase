package com.example.roomdatabase.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.roomdatabase.data.repository.student.Student
import com.example.roomdatabase.utils.DATABASE_VERSION

@Database(entities = [Student::class], version = DATABASE_VERSION)
abstract class AppDatabase : RoomDatabase() {

    abstract fun studentDao() : StudentDao
}