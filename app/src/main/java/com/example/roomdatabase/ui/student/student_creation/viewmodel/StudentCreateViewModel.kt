package com.example.roomdatabase.ui.student.student_creation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomdatabase.core.DataFetchCallback
import com.example.roomdatabase.data.repository.student.Student
import com.example.roomdatabase.data.repository.student.StudentRepository
import kotlinx.coroutines.launch

class StudentCreateViewModel(private val repository: StudentRepository) : ViewModel() {
    val studentCreationSuccessLiveData = MutableLiveData<Unit>()
    val studentCreationFailedLiveData = MutableLiveData<String>()

    fun createStudent(student: Student){
        viewModelScope.launch {
            repository.createStudent(student, object : DataFetchCallback<Long> {
                override fun onSuccess(data: Long) {
                    studentCreationSuccessLiveData.postValue(Unit)
                }

                override fun onError(throwable: Throwable) {
                   studentCreationFailedLiveData.postValue(throwable.localizedMessage)
                }

            })
        }
    }
}