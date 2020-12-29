package com.example.roomdatabase.data.local

interface StudentDataSetChangeListener {
    fun onStudentDataChanged()
    fun onStudentDataSetChangeError(error: String)
}