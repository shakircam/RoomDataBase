package com.example.roomdatabase.ui.student.student_creation.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.roomdatabase.R
import com.example.roomdatabase.data.local.StudentDataSetChangeListener
import com.example.roomdatabase.data.repository.student.Student
import com.example.roomdatabase.data.repository.student.StudentRepositoryImpl
import com.example.roomdatabase.databinding.FragmentStudentCreateDialogBinding
import com.example.roomdatabase.ui.student.student_creation.viewmodel.StudentCreateViewModel
import com.example.roomdatabase.ui.student.student_creation.viewmodel.StudentCreateViewModelFactory

class StudentCreateDialogFragment : DialogFragment() {

    private var binding: FragmentStudentCreateDialogBinding? = null

    private val repository by lazy { StudentRepositoryImpl(requireContext().applicationContext) }
    private val viewModel by lazy {

        val factory = StudentCreateViewModelFactory(repository)
        ViewModelProvider(this, factory).get(StudentCreateViewModel::class.java)
    }
    private lateinit var studentDataSetChangeListener: StudentDataSetChangeListener

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is StudentDataSetChangeListener) {
            studentDataSetChangeListener = context
        } else
            throw ClassCastException("Caller class must implement StudentCrudListener interface")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStudentCreateDialogBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.setTitle(getString(R.string.title_create_student))
        binding?.createButton?.setOnClickListener {

            val name = binding?.studentNameEditText?.text.toString()
            val registrationNumber = binding?.registrationEditText?.text.toString()
            val phoneNumber = binding?.phoneEditText?.text.toString()
            val email = binding?.emailEditText?.text.toString()

            if (name.isEmpty() || registrationNumber.isEmpty() || phoneNumber.isEmpty() || email.isEmpty()) {
                Toast.makeText(requireContext(), "All fields are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val student = Student(
                name = name,
                registrationNumber = registrationNumber.toLong(),
                phoneNumber = phoneNumber,
                email = email
            )

            viewModel.createStudent(student)
        }
        binding?.cancelButton?.setOnClickListener {
            dismiss()
        }
        viewModel.studentCreationSuccessLiveData.observe(viewLifecycleOwner, {
            studentDataSetChangeListener.onStudentDataChanged()
            dismiss()
        })

        viewModel.studentCreationFailedLiveData.observe(viewLifecycleOwner, {
            studentDataSetChangeListener.onStudentDataSetChangeError(it)
        })
    }
    override fun onStart() {
        super.onStart()

        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.WRAP_CONTENT

        dialog?.window?.setLayout(width, height)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}