package com.example.trackme.ui.screens.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.domain.models.Task
import com.example.trackme.R
import com.example.trackme.databinding.FragmentTaskAddBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class TaskAddFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentTaskAddBinding? = null
    private val binding get() = _binding!!

    private val taskViewModel: TaskViewModel by activityViewModels()

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
                    "",
                    false,
                    "daw",
                    "dawda",
                    "dawda"
                )
            )
            findNavController().popBackStack()
        }

        return binding.root
    }

}