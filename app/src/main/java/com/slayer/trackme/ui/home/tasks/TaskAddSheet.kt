package com.slayer.trackme.ui.home.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.slayer.trackme.R
import com.slayer.trackme.databinding.SheetTaskAddBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaskAddSheet : BottomSheetDialogFragment() {
    private var _binding: SheetTaskAddBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SheetTaskAddBinding.inflate(inflater, container, false)

        binding.btnPriority.setOnClickListener {

        }

        binding.btnDueDate.setOnClickListener {

        }

        binding.btnCategory.setOnClickListener {

        }

        observeTaskNameTextChange()

        handleSaveButtonClick()

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    private fun observeTaskNameTextChange() {
        binding.apply {
            etTaskName.addTextChangedListener { btnSave.isEnabled = it.toString().isNotEmpty() }
        }
    }

    private fun handleSaveButtonClick() {
        binding.apply {
            btnSave.setOnClickListener {
                val taskName = etTaskName.text.toString().replaceFirstChar { it.titlecase() }

                viewModel.insertList(taskName)

                dismiss()
            }
        }
    }
}