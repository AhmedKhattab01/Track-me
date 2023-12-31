package com.example.trackme.ui.auth.login

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.common_ui.Utils.toast
import com.example.trackme.R
import com.example.trackme.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        val fullText = getString(R.string.don_t_have_account_sign_up)

        // Locate the position of the specific word you want to highlight
        val startIndex = 20
        val endIndex = startIndex + 7

        // Create a SpannableString
        val spannableString = SpannableString(fullText)

        // Apply a different color to the specific word
        spannableString.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(requireContext(),R.color.md_theme_light_primary)),
            startIndex,
            endIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        // Set the SpannableString to the TextView
        binding.textView6.text = spannableString

        handleLoginBtnClick()

        // Create google sign in options
//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(getString(R.string.default_web_client_id))
//            .requestEmail()
//            .build()
//
//        val googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
//
//        val signInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//            if (result.resultCode == Activity.RESULT_OK) {
//                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
//                try {
//                    // Google Sign-In was successful, authenticate with Firebase
//                    val account = task.getResult(ApiException::class.java)!!
//                    firebaseAuthWithGoogle(account.idToken!!)
//                } catch (e: ApiException) {
//                    // Google Sign-In failed
//                    Log.w("rabbit", "Google sign in failed", e)
//                }
//            }
//        }

//        binding.button3.setOnClickListener {
//            val signInIntent = googleSignInClient.signInIntent
//            signInLauncher.launch(signInIntent)
//        }
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun handleLoginBtnClick() {
        binding.apply {
            btnLogin.setOnClickListener {
                val email = etEmail.text.toString().trim().lowercase()
                val password = etPassword.text.toString().trim()

                lifecycleScope.launch {
                    viewModel.tryLogin(email, password)

                    if (viewModel.loginResult.value?.user != null) {
                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    }
                    else {
                        toast(viewModel.handleSignInWithEmailAndPasswordException())
                    }
                }
            }
        }
    }

//    private fun firebaseAuthWithGoogle(idToken: String) {
//        // get credential
//        val credential = GoogleAuthProvider.getCredential(idToken, null)
//
//        // Sign in with credential
//        auth.signInWithCredential(credential)
//            .addOnCompleteListener(requireActivity()) { task ->
//                if (task.isSuccessful) {
//                    // Authentication with Firebase was successful
//                    val user = auth.currentUser
//
//                    findNavController().navigate(R.id.action_loginFragment_to_listFragment)
//
//                } else {
//                    // Authentication with Firebase failed
//                    Log.w("rabbit", "signInWithCredential:failure", task.exception)
//                }
//            }
//    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }
}