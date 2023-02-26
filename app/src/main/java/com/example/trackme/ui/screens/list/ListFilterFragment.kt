package com.example.trackme.ui.screens.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.trackme.R
import com.example.trackme.databinding.FragmentListFilterBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class ListFilterFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentListFilterBinding? = null
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
        _binding = FragmentListFilterBinding.inflate(inflater, container, false)

        val filterValues = mapOf(
            R.id.rb_default to 0,
            R.id.rb_ascending to 1,
            R.id.rb_descending to 2,
        )

        // Set the selected radio button based on the current filter value
        binding.radioGroup.check(
            filterValues.entries.firstOrNull { it.value == listViewModel.filter.value }?.key ?: R.id.rb_default
        )

        // Update the filter value when a radio button is selected
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            filterValues[checkedId]?.let { listViewModel.setFilter(it) }
            findNavController().popBackStack()
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}