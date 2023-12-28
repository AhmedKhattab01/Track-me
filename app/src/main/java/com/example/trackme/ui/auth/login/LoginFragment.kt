package com.example.trackme.ui.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
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

                    Toast.makeText(
                        requireContext(),
                        "login success ${viewModel.loginResult.value}",
                        Toast.LENGTH_SHORT
                    ).show()

                    if (viewModel.loginResult.value?.user != null) {
                        Toast.makeText(
                            requireContext(),
                            "login success ${viewModel.loginResult.value}",
                            Toast.LENGTH_SHORT
                        ).show()

                    } else {
                        Toast.makeText(requireContext(), "Login failed", Toast.LENGTH_SHORT).show()
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