package com.example.roomdatabase.data.repository.student

import com.example.roomdatabase.core.DataFetchCallback

interface StudentRepository {
    suspend fun createStudent(student: Student, callback: DataFetchCallback<Long>)
    suspend fun getStudentList(callback: DataFetchCallback<MutableList<Student>>)
    suspend fun updateStudent(student: Student,callback: DataFetchCallback<Int>)
    suspend fun deleteStudent(student: Student,callback: DataFetchCallback<Int>)
}