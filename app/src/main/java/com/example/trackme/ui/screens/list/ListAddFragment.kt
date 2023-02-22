package com.example.trackme.ui.screens.list

import android.app.Application
import android.os.Bundle
import android.util.Log
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
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jaredrummler.android.colorpicker.ColorPickerDialog
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener
import com.maltaisn.icondialog.IconDialog
import com.maltaisn.icondialog.IconDialogSettings
import com.maltaisn.icondialog.data.Icon
import com.maltaisn.icondialog.pack.IconPack
import com.maltaisn.icondialog.pack.IconPackLoader
import com.maltaisn.iconpack.defaultpack.createDefaultIconPack
import dagger.hilt.EntryPoint
import javax.inject.Inject


class ListAddFragment : BottomSheetDialogFragment(), ColorPickerDialogListener,IconDialog.Callback {
    private var _binding: FragmentListAddBinding? = null
    private val binding get() = _binding!!

    private val listViewModel: ListViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentListAddBinding.inflate(inflater, container, false)

        listViewModel.icons.loadDrawables(listViewModel.iconLoader.drawableLoader)

        setBtnColorFilter()

        with(binding) {
            // Enable save button when text is not equal null or empty
            etList.addTextChangedListener {
                binding.btnSave.isEnabled = it.toString().isNotEmpty()
            }

            btnIcon.setOnClickListener {
                val iconDialog =IconDialog.newInstance(IconDialogSettings())
                iconDialog.show(childFragmentManager,"MAA")
            }
            listViewModel.icons.getIconDrawable(333,listViewModel.iconLoader.drawableLoader)
            // insert item on save button clicked
            btnSave.setOnClickListener {
                listViewModel.insertList(
                    TaskList(
                        0,
                        binding.etList.text.toString().replaceFirstChar { it.titlecase() },
                        0,
                        currentDate,
                        listViewModel.isFavourite,
                        listViewModel.color
                    )
                )
                findNavController().popBackStack()
            }

            // Show color picker dialog
            btnColor.setOnClickListener {
                val colorPickerDialog = ColorPickerDialog
                    .newBuilder()
                    .setColor(listViewModel.color)
                    .create()

                colorPickerDialog.setColorPickerDialogListener(this@ListAddFragment)
                colorPickerDialog.show(requireActivity().supportFragmentManager, "Foo")
            }
        }
        return binding.root
    }

    override fun onColorSelected(dialogId: Int, color: Int) {
        listViewModel.color = color
        setBtnColorFilter()
    }

    override fun onDialogDismissed(dialogId: Int) {

    }

    // Set color button color filter
    private fun setBtnColorFilter() {
        binding.btnColor.setColorFilter(listViewModel.color)
        binding.btnIcon.setColorFilter(listViewModel.color)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override val iconDialogIconPack: IconPack
        get() = listViewModel.icons

    override fun onIconDialogIconsSelected(dialog: IconDialog, icons: List<Icon>) {
        binding.btnIcon.setImageDrawable(icons[0].drawable)
    }
}