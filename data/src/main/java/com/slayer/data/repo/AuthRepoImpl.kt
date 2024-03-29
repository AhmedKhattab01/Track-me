package com.slayer.data.repo

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.slayer.domain.models.TaskResult
import com.slayer.domain.repo.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AuthRepoImpl(private val firebaseAuth: FirebaseAuth) : AuthRepository {
    val TAG : String = this.javaClass.simpleName

    override suspend fun login(email: String, password: String): TaskResult<AuthResult> {
        return withContext(Dispatchers.IO) {
            try {
                val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
                TaskResult.Success(result)
            } catch (e : Exception) {
                TaskResult.Failure(e.message,-1,e)
            }
        }
    }

    override suspend fun loginWithGoogle(token: String): TaskResult<AuthResult> {
        return withContext(Dispatchers.IO) {
            try {
                val credential = GoogleAuthProvider.getCredential(token, null)
                val result = firebaseAuth.signInWithCredential(credential).await()
                // You can return the AuthResult or any relevant information here
                TaskResult.Success(result)
            } catch (e: Exception) {
                // Handle the exception or return an appropriate error message
                TaskResult.Failure(e.message,-1)
            }
        }
    }

    override suspend fun register(email: String, password: String): TaskResult<AuthResult> {
        return withContext(Dispatchers.IO) {
            try {
                val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
                TaskResult.Success(result)
            } catch (e : Exception) {
                TaskResult.Failure(e.message,-1,e)
            }
        }
    }

    override suspend fun forgetPassword(email: String): TaskResult<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                firebaseAuth.sendPasswordResetEmail(email).await()
                TaskResult.Success(true)
            } catch (e : Exception) {
                TaskResult.Failure(e.message,-1,e)
            }
        }
    }

    override fun logout() : Flow<TaskResult<Boolean>> {
        return callbackFlow {
            firebaseAuth.signOut()

            if (firebaseAuth.currentUser == null) {
                trySend(TaskResult.Success(true))
            } else {
                trySend(TaskResult.Success(false))
            }
        }
    }
}