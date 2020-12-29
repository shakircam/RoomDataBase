package com.example.roomdatabase.ui.student.student_list.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdatabase.R
import com.example.roomdatabase.core.BaseActivity
import com.example.roomdatabase.data.local.StudentDataSetChangeListener
import com.example.roomdatabase.data.repository.student.Student
import com.example.roomdatabase.data.repository.student.StudentRepositoryImpl
import com.example.roomdatabase.databinding.ActivityStudentListBinding
import com.example.roomdatabase.ui.student.student_creation.view.StudentCreateDialogFragment
import com.example.roomdatabase.ui.student.student_list.viewmodel.StudentListViewModel
import com.example.roomdatabase.ui.student.student_list.viewmodel.StudentListViewModelFactory
import com.example.roomdatabase.ui.student.student_update.view.StudentUpdateFragment
import com.example.roomdatabase.ui.subject.subject_list.SubjectListActivity
import com.example.roomdatabase.utils.CREATE_STUDENT
import com.example.roomdatabase.utils.STUDENT_REGISTRATION
import com.example.roomdatabase.utils.UPDATE_STUDENT
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class StudentListActivity : BaseActivity(), StudentDataSetChangeListener {

    private lateinit var binding: ActivityStudentListBinding
    private val repository by lazy {
        StudentRepositoryImpl(applicationContext)
    }

    private val viewModel by lazy {
        val factory = StudentListViewModelFactory(repository)
        ViewModelProvider(this, factory).get(StudentListViewModel::class.java)
    }

    private val studentList by lazy { mutableListOf<Student>() }

    private val studentListAdapter by lazy { StudentListAdapter(studentList,
        object : StudentListAdapter.StudentListClickListener {
            override fun onItemClicked(registrationNumber: Long) {
                showSubjectListActivity(registrationNumber)
            }

            override fun onEditButtonClicked(student: Student) {
                showStudentEditDialog(student)
            }

            override fun onDeleteButtonClicked(student: Student) {
                showStudentDeleteDialog(student)
            }

        }) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarLayout.toolbar.title = getString(R.string.app_name)

        initRecyclerView()

        viewModel.getStudentList()

        viewModel.studentListLiveData.observe(this, {
            studentListAdapter.replaceData(it)
        })

        viewModel.studentListFailedLiveData.observe(this, {
            showToast(it)
        })

        viewModel.studentDeletionSuccessLiveData.observe(this, {
            viewModel.getStudentList()
            showToast("$it item is deleted")
        })

        viewModel.studentDeletionFailedLiveData.observe(this, {
            showToast(it)
        })

        binding.btnAddStudent.setOnClickListener {
            showStudentCreationDialog()
        }
    }

    override fun onStudentDataChanged() {
        viewModel.getStudentList()
    }

    override fun onStudentDataSetChangeError(error: String) {
        showToast(error)
    }

    private fun initRecyclerView(){
        binding.recyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        binding.recyclerView.adapter = studentListAdapter
    }

    private fun showStudentCreationDialog(){
        val dialogFragment = StudentCreateDialogFragment()
        dialogFragment.setStyle(DialogFragment.STYLE_NORMAL,R.style.CustomDialog)
        dialogFragment.show(supportFragmentManager, CREATE_STUDENT)
    }
    private fun showStudentEditDialog(studentId: Student) {
        val updateDialogFragment = StudentUpdateFragment()
        updateDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog)

        updateDialogFragment.show(supportFragmentManager, UPDATE_STUDENT)
        showToast(studentId.toString())
    }
    private fun showStudentDeleteDialog(studentId: Student) {
        var dialog: AlertDialog? = null

        dialog = MaterialAlertDialogBuilder(this)
            .setMessage(getString(R.string.student_delete_alert))
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                viewModel.deleteStudent(studentId)
            }
            .setNegativeButton(getString(R.string.no)) { _, _ ->
                dialog?.dismiss()
            }
            .show()
    }
    private fun showSubjectListActivity(registrationNumber: Long) {
        val bundle = Bundle()
        bundle.putLong(STUDENT_REGISTRATION, registrationNumber)
        val intent = Intent(this, SubjectListActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }
}