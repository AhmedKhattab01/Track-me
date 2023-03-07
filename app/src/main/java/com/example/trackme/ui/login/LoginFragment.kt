package com.example.trackme.ui.login

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.trackme.R
import com.example.trackme.databinding.FragmentLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        auth = FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener {
            auth.signInWithEmailAndPassword("akhaled.vi@gmail.com", "Project00f30!")
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        // TODO : NAVIGATE TO LIST SCREEN
                        findNavController().navigate(R.id.action_loginFragment_to_listFragment)
                    } else {
                        Log.d("rabbit", "onCreateView: ${it.exception?.message}")
                    }
                }
        }

        // Create google sign in options
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)

        val signInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    // Google Sign-In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    // Google Sign-In failed
                    Log.w("rabbit", "Google sign in failed", e)
                }
            }
        }

        binding.button3.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            signInLauncher.launch(signInIntent)
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        // get credential
        val credential = GoogleAuthProvider.getCredential(idToken, null)

        // Sign in with credential
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Authentication with Firebase was successful
                    val user = auth.currentUser

                    findNavController().navigate(R.id.action_loginFragment_to_listFragment)

                } else {
                    // Authentication with Firebase failed
                    Log.w("rabbit", "signInWithCredential:failure", task.exception)
                }
            }
    }
}