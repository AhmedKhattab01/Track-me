package com.example.trackme.ui.screens.list

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
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jaredrummler.android.colorpicker.ColorPickerDialog
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener

class ListAddFragment : BottomSheetDialogFragment(), ColorPickerDialogListener {
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

        setBtnColorFilter()

        with(binding) {
            // Enable save button when text is not equal null or empty
            etList.addTextChangedListener {
                binding.btnSave.isEnabled = it.toString().isNotEmpty()
            }

            // Change favourite state and drawable of the button on button clicked
            btnFavourite.setOnClickListener {
                listViewModel.isFavourite = !listViewModel.isFavourite
                val iconResource =
                    if (listViewModel.isFavourite) R.drawable.baseline_star_24 else R.drawable.baseline_star_outline_24
                binding.btnFavourite.setImageResource(iconResource)
            }

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
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}