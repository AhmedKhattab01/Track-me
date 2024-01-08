package com.slayer.trackme.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.slayer.trackme.R
import com.slayer.trackme.common.Utils.visibleIf
import com.slayer.trackme.databinding.FragmentOnboardingBinding
import com.slayer.trackme.ui.onboarding.first.FirstOnboardingFragment
import com.slayer.trackme.ui.onboarding.second.SecondOnboardingFragment
import com.slayer.trackme.ui.onboarding.third.ThirdOnboardingFragment

class OnboardingFragment : Fragment() {

    private var _binding: FragmentOnboardingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentOnboardingBinding.inflate(inflater, container, false)

        setupViewPager()

        handlePageChange()

        handleSkipBtnClick()

        return binding.root
    }

    private fun handleSkipBtnClick() {
        binding.btnSkip.setOnClickListener { findNavController().navigate(R.id.action_onboardingFragment_to_authentication) }
    }

    private fun setupViewPager() {
        // creating arraylist of fragments required for the adapter
        val fragmentList = arrayListOf(
            FirstOnboardingFragment(),
            SecondOnboardingFragment(),
            ThirdOnboardingFragment()
        )

        // assigning on boarding adapter object
        val adapter = OnboardingAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        binding.viewpager.adapter = adapter
        binding.dotsIndicator.attachTo(binding.viewpager)
    }

    private fun handlePageChange() {
        binding.apply {
            viewpager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)

                    btnBack.visibleIf(position != 0)

                    when (position) {
                        0 -> {
                            btnNext.text = getString(R.string.next)
                            btnNext.setOnClickListener { viewpager.currentItem = 1 }
                        }

                        1 -> {
                            btnNext.text = getString(R.string.next)
                            btnNext.setOnClickListener { viewpager.currentItem = 2 }
                            btnBack.setOnClickListener { viewpager.currentItem = 0 }
                        }

                        2 -> {
                            btnNext.text = getString(R.string.get_started)
                            btnNext.setOnClickListener { findNavController().navigate(R.id.action_onboardingFragment_to_authentication) }
                            btnBack.setOnClickListener { viewpager.currentItem = 1 }
                        }
                    }
                }
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}