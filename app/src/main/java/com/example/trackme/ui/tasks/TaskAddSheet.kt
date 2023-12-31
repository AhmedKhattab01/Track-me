package com.example.trackme.ui.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.domain.models.task.Task
import com.example.trackme.R
import com.example.trackme.databinding.SheetTaskAddBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.skydoves.colorpickerview.ColorPickerDialog

class TaskAddSheet : BottomSheetDialogFragment(){
    private var _binding: SheetTaskAddBinding? = null
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
        _binding = SheetTaskAddBinding.inflate(inflater, container, false)

        with(binding) {
            // Enable save button when text is not equal null or empty
            etList.addTextChangedListener { btnSave.isEnabled = it.toString().isNotEmpty() }

            // Color
            btnColor.setOnClickListener {
                ColorPickerDialog.Builder(requireContext(), R.style.MyColorPickerDialogTheme)
                    .setTitle("ColorPicker Dialog")
                    .setPreferenceName("MyColorPickerDialog")
                    .setPositiveButton("Confirm") { envelope, fromUser ->

                    }
                    .setNegativeButton("Cancel") { dialogInterface, _ ->
                        dialogInterface.dismiss()
                    }
                    .attachAlphaSlideBar(true)
                    .attachBrightnessSlideBar(true)
                    .setBottomSpace(12)
                    .show()

            }

            // Save button
            btnSave.setOnClickListener {
                val listName = etList.text.toString().replaceFirstChar { it.titlecase() }
                val taskList = Task(0, listName)
                taskViewModel.insertList(taskList)
                dismiss()
            }
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}