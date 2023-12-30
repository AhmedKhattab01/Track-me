package com.example.trackme.ui.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.data.utils.currentDate
import com.example.domain.models.TaskList
import com.example.trackme.R
import com.example.trackme.databinding.SheetTaskAddBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jaredrummler.android.colorpicker.ColorPickerDialog
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener

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

            // Icon
            btnIcon.setColorFilter(taskViewModel.color)

            // Color
            btnColor.setOnClickListener {
                ColorPickerDialog
                    .newBuilder()
                    .setColor(taskViewModel.color)
                    .create().also {
                        it.setColorPickerDialogListener(object : ColorPickerDialogListener {
                            override fun onColorSelected(dialogId: Int, color: Int) {
                                taskViewModel.color = color
                                btnColor.setColorFilter(taskViewModel.color)
                                btnIcon.setColorFilter(taskViewModel.color)
                            }
                            override fun onDialogDismissed(dialogId: Int) {}
                        })
                        it.show(requireActivity().supportFragmentManager, "Foo")
                    }
            }
            btnColor.setColorFilter(taskViewModel.color)

            // Save button
            btnSave.setOnClickListener {
                val listName = etList.text.toString().replaceFirstChar { it.titlecase() }
                val taskList = TaskList(0, listName, currentDate, color = taskViewModel.color, iconId = -1)
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