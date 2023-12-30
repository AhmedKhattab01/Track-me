package com.example.trackme.ui.screens.todo.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.trackme.R
import com.example.trackme.databinding.SheetTaskAddBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class TaskAddFragment : BottomSheetDialogFragment() {
    private var _binding: SheetTaskAddBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SheetTaskAddBinding.inflate(inflater, container, false)


//        // Enable save button when text is not equal null or empty
//        binding.etTask.addTextChangedListener {
//            binding.btnSave.isEnabled = it.toString().isNotEmpty()
//        }
//
//        binding.btnSave.setOnClickListener {
//            taskViewModel.insertTask(
//                Task(
//                    0,
//                    binding.etTask.text.toString(),
//                    creationDate = currentDate,
//                    listType = args.taskList.name
//                )
//            )
//
//            args.taskList.totalTasks += 1
//            taskTaskViewModel.updateList(args.taskList)
//
//            findNavController().popBackStack()
//        }

        return binding.root
    }
}