package com.example.trackme.ui.home.tasks

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.trackme.R
import com.example.trackme.TrackApplication
import com.example.trackme.databinding.SheetTaskAddBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.maltaisn.icondialog.IconDialog
import com.maltaisn.icondialog.IconDialogSettings
import com.maltaisn.icondialog.data.Icon
import com.maltaisn.icondialog.pack.IconPack
import com.maltaisn.icondialog.pack.IconPackLoader
import com.skydoves.colorpickerview.ColorPickerDialog
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaskAddSheet : BottomSheetDialogFragment(), IconDialog.Callback {
    private var _binding: SheetTaskAddBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TaskViewModel by viewModels()
    private val loader by lazy { IconPackLoader(requireContext()) }

    companion object {
        private const val ICON_DIALOG_TAG = "icon-dialog"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SheetTaskAddBinding.inflate(inflater, container, false)

        val iconDialog = this.childFragmentManager.findFragmentByTag(ICON_DIALOG_TAG) as IconDialog?
            ?: IconDialog.newInstance(
                IconDialogSettings()
            )

        binding.btnIcon.setOnClickListener {
            iconDialog.show(this.childFragmentManager, ICON_DIALOG_TAG)
        }

        observeTaskNameTextChange()

        handleColorBtnClick()
        handleSaveButtonClick()

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun changeViewsColor(colorHex: String) {
        binding.apply {
            btnColor.setColorFilter(Color.parseColor(colorHex))
            btnIcon.setColorFilter(Color.parseColor(colorHex))
        }
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

    private fun handleColorBtnClick() {
        binding.btnColor.setOnClickListener {
            openColorPickerDialog()
        }
    }

    private fun openColorPickerDialog() {
        val envelopeListener = ColorEnvelopeListener { envelope, fromUser ->
            val colorHex = "#${envelope.hexCode}"
            viewModel.setSelectedColorHexCode(colorHex)
            changeViewsColor(colorHex)
        }

        ColorPickerDialog.Builder(requireContext(), R.style.MyColorPickerDialogTheme)
            .setTitle(getString(R.string.pick_a_color))
            .attachAlphaSlideBar(true)
            .attachBrightnessSlideBar(true)
            .setBottomSpace(12)
            .setPositiveButton(
                getString(R.string.confirm),
                envelopeListener
            )
            .setNegativeButton(getString(R.string.cancel)) { dialogInterface, i ->
                dialogInterface.dismiss()
            }.show()
    }

    override val iconDialogIconPack: IconPack
        get() = (requireActivity().application as TrackApplication).iconPack

    override fun onIconDialogIconsSelected(dialog: IconDialog, icons: List<Icon>) {
        val selectedDrawable =
            iconDialogIconPack.getIconDrawable(icons[0].id, loader.drawableLoader)

        binding.btnIcon.setImageDrawable(
            selectedDrawable
        )

        viewModel.setSelectedIconId(icons[0].id)
    }
}