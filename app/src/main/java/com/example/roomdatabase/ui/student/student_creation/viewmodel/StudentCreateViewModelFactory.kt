package com.example.roomdatabase.ui.student.student_creation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.roomdatabase.data.repository.student.StudentRepository
import java.lang.IllegalArgumentException

class StudentCreateViewModelFactory(val repository: StudentRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StudentCreateViewModel::class.java)){
            return StudentCreateViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}