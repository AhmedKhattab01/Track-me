package com.example.trackme.ui.screens.todo.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.data.utils.currentDate
import com.example.domain.models.Task
import com.example.trackme.R
import com.example.trackme.databinding.FragmentTaskAddBinding
import com.example.trackme.ui.screens.todo.tasks_lists.ListViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class TaskAddFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentTaskAddBinding? = null
    private val binding get() = _binding!!

    private val taskViewModel: TaskViewModel by activityViewModels()
    private val taskListViewModel : ListViewModel by activityViewModels()


    private val args by navArgs<TaskAddFragmentArgs>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskAddBinding.inflate(inflater, container, false)


        // Enable save button when text is not equal null or empty
        binding.etTask.addTextChangedListener {
            binding.btnSave.isEnabled = it.toString().isNotEmpty()
        }

        binding.btnSave.setOnClickListener {
            taskViewModel.insertTask(
                Task(
                    0,
                    binding.etTask.text.toString(),
                    creationDate = currentDate,
                    listType = args.taskList.name
                )
            )

            args.taskList.totalTasks += 1

            taskListViewModel.updateList(args.taskList)
            findNavController().popBackStack()
        }

        return binding.root
    }
}