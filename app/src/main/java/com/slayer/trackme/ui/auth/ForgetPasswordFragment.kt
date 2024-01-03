package com.slayer.trackme.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.slayer.common.ValidationUtil
import com.slayer.trackme.R
import com.slayer.trackme.databinding.FragmentForgetPasswordBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ForgetPasswordFragment : Fragment() {
    private val TAG = this.javaClass.simpleName

    private var _binding: FragmentForgetPasswordBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ForgetPasswordViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForgetPasswordBinding.inflate(inflater, container, false)

        binding.apply {
            btnForget.setOnClickListener {
                val email = containerEmail.editText?.text.toString().trim().lowercase()

                if (email.isEmpty()) {
                    containerEmail.error = getString(R.string.email_address_is_required_please_enter_a_valid_email)
                    return@setOnClickListener
                }

                if (!ValidationUtil.isValidEmailAddress(email)) {
                    containerEmail.error = getString(R.string.invalid_email_address_please_enter_a_valid_email)
                    return@setOnClickListener
                }

                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.forgetPassword(email)

                    if (viewModel.forgetPasswordResult.value == true) {
                        val alertDialog = AlertDialog.Builder(requireContext(),R.style.MyColorPickerDialogTheme)
                            .setTitle("Password Reset Email Sent")
                            .setMessage("An email has been sent to you for password reset. Please check your inbox.")
                            .setPositiveButton("Dismiss") { _, _ ->
                                findNavController().navigateUp()
                            }
                            .setCancelable(false)
                            .create()

                        alertDialog.show()
                    }
                    else {
                        containerEmail.error = getString(viewModel.handleForgetPasswordExceptions())
                    }
                }
            }
        }

        return binding.root
    }
}