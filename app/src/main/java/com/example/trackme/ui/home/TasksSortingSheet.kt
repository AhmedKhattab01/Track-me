package com.example.trackme.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.trackme.R
import com.example.trackme.databinding.SheetTasksSortingBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class TasksSortingSheet : BottomSheetDialogFragment() {
    private var _binding: SheetTasksSortingBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels(
        ownerProducer = { requireParentFragment() }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SheetTasksSortingBinding.inflate(inflater, container, false)

        val filterValues = mapOf(
            R.id.rb_default to 0,
            R.id.rb_ascending to 1,
            R.id.rb_descending to 2,
        )

        // Set the selected radio button based on the current filter value
        binding.radioGroup.check(
            filterValues.entries.first {
                it.value == viewModel.sortResult.value
            }.key
        )

        // Update the filter value when a radio button is selected
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            filterValues[checkedId]?.let { viewModel.setSort(it) }
            dismiss()
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}