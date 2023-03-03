package com.example.trackme.ui.screens.todo.tasks_lists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.data.utils.currentDate
import com.example.domain.models.TaskList
import com.example.trackme.R
import com.example.trackme.databinding.FragmentListAddBinding
import com.example.trackme.ui.shared_viewmodels.IconViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jaredrummler.android.colorpicker.ColorPickerDialog
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener
import com.maltaisn.icondialog.IconDialog
import com.maltaisn.icondialog.IconDialogSettings
import com.maltaisn.icondialog.data.Icon
import com.maltaisn.icondialog.pack.IconPack

class ListAddFragment : BottomSheetDialogFragment(), IconDialog.Callback {
    private var _binding: FragmentListAddBinding? = null
    private val binding get() = _binding!!

    private val listViewModel: ListViewModel by activityViewModels()
    private val iconViewModel: IconViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListAddBinding.inflate(inflater, container, false)

        with(binding) {
            // Enable save button when text is not equal null or empty
            etList.addTextChangedListener { btnSave.isEnabled = it.toString().isNotEmpty() }

            // Icon
            iconViewModel.iconPack.loadDrawables(iconViewModel.drawableLoader)
            btnIcon.setOnClickListener { IconDialog.newInstance(IconDialogSettings()).show(childFragmentManager, "Faa") }
            btnIcon.setColorFilter(listViewModel.color)

            // Color
            btnColor.setOnClickListener {
                ColorPickerDialog
                    .newBuilder()
                    .setColor(listViewModel.color)
                    .create().also {
                        it.setColorPickerDialogListener(object : ColorPickerDialogListener {
                            override fun onColorSelected(dialogId: Int, color: Int) {
                                listViewModel.color = color
                                btnColor.setColorFilter(listViewModel.color)
                                btnIcon.setColorFilter(listViewModel.color)
                            }
                            override fun onDialogDismissed(dialogId: Int) {}
                        })
                        it.show(requireActivity().supportFragmentManager, "Foo")
                    }
            }
            btnColor.setColorFilter(listViewModel.color)

            // Save button
            btnSave.setOnClickListener {
                val listName = etList.text.toString().replaceFirstChar { it.titlecase() }
                val taskList = TaskList(0, listName, currentDate, color = listViewModel.color, iconId = iconViewModel.iconId)
                listViewModel.insertList(taskList)
                findNavController().popBackStack()
            }
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override val iconDialogIconPack: IconPack
        get() = iconViewModel.iconPack

    override fun onIconDialogIconsSelected(dialog: IconDialog, icons: List<Icon>) {
        binding.btnIcon.setImageDrawable(icons[0].drawable)
        iconViewModel.iconId = icons[0].id
    }
}