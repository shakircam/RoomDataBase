package com.example.roomdatabase.ui.student.student_update.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomdatabase.core.DataFetchCallback
import com.example.roomdatabase.data.repository.student.Student
import com.example.roomdatabase.data.repository.student.StudentRepository
import kotlinx.coroutines.launch

class StudentUpdateViewModel(private val repository: StudentRepository) : ViewModel() {

    val studentUpdateSuccessLiveData = MutableLiveData<Unit>()
    val studentUpdateFailedLiveData = MutableLiveData<String>()

    fun updateStudent(student: Student){
        viewModelScope.launch {
            repository.updateStudent(student, object : DataFetchCallback<Int> {
                override fun onSuccess(data: Int) {
                    studentUpdateSuccessLiveData.postValue(Unit)
                }

                override fun onError(throwable: Throwable) {
                    studentUpdateFailedLiveData.postValue(throwable.localizedMessage)
                }

            })
        }
    }
}