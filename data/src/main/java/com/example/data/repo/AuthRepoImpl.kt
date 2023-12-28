package com.example.data.repo

import com.example.domain.models.TaskResult
import com.example.domain.repo.AuthRepository
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AuthRepoImpl(private val firebaseAuth: FirebaseAuth) : AuthRepository {
    val tag : String = this.javaClass.simpleName

    override suspend fun login(email: String, password: String): TaskResult<AuthResult> {
        return withContext(Dispatchers.IO) {
            try {
                val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
                val uid = result.user?.uid!!

                TaskResult.Success(result)
            } catch (e : Exception) {
                TaskResult.Failure(e.message,-1)
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