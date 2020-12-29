package com.example.roomdatabase.data.repository.student

import android.content.Context

import com.example.roomdatabase.core.DataFetchCallback
import com.example.roomdatabase.data.local.DatabaseBuilder
import com.orhanobut.logger.Logger
import java.lang.Exception


class StudentRepositoryImpl(context: Context) : StudentRepository {

    private val db = DatabaseBuilder.getInstance(context)
    private val studentDao = db.studentDao()

    override suspend fun createStudent(student: Student, callback: DataFetchCallback<Long>) {
        try {
            callback.onSuccess(studentDao.insert(student))
        }catch (e:Exception){
            callback.onError(e)
            Logger.e(e.localizedMessage)
        }

    }

    override suspend fun getStudentList(callback: DataFetchCallback<MutableList<Student>>) {
        try {
            callback.onSuccess(studentDao.getAll())
        }catch (e:Exception){
            callback.onError(e)
            Logger.e(e.localizedMessage)
        }
    }

    override suspend fun updateStudent(student: Student, callback: DataFetchCallback<Int>) {
        try {
            callback.onSuccess(studentDao.update(student))
        }catch (e:Exception){
            callback.onError(e)
            Logger.e(e.localizedMessage)
        }
    }

    override suspend fun deleteStudent(student: Student, callback: DataFetchCallback<Int>) {
        try {
            callback.onSuccess(studentDao.delete(student))
        }catch (e:Exception){
            callback.onError(e)
            Logger.e(e.localizedMessage)
        }
    }
}