package com.techlogics.instagramclone.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.techlogics.instagramclone.domain.model.User
import com.techlogics.instagramclone.domain.repository.AuthenticationRepository
import com.techlogics.instagramclone.util.Constants
import com.techlogics.instagramclone.util.Response
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
       private val auth: FirebaseAuth,
       private val fireStore:FirebaseFirestore

       ):AuthenticationRepository{
    var operationSuccessfull:Boolean = false

    override fun isUserAuthenticatedInFirebase(): Boolean {
        return auth.currentUser!=null
     }

    @ExperimentalCoroutinesApi
    override fun getFirebaseAuthState(): Flow<Boolean> = callbackFlow {
        val authStateListener = FirebaseAuth.AuthStateListener {
           trySend(auth.currentUser!=null)
        }
        auth.addAuthStateListener(authStateListener)
        awaitClose{
            auth.removeAuthStateListener(authStateListener)
        }

    }

    override fun firebaseSignIn(email: String, password: String): Flow<Response<Boolean>> = flow{
           operationSuccessfull = false
        try{
            emit(Response.Loading)
            auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                operationSuccessfull = true

            }.await()
            emit(Response.Success(operationSuccessfull))

        }catch (exception:Exception){
            emit(Response.Error("Error Occured:"+exception.localizedMessage))
        }
    }

    override fun firebaseSignOut(): Flow<Response<Boolean>> = flow {

        emit(Response.Loading)
        auth.signOut()
        emit(Response.Success(true))

    }

    override fun firebaseSignUp(
        email: String,
        password: String,
        username: String
    ): Flow<Response<Boolean>> = flow {

        operationSuccessfull = false
        try{
            emit(Response.Loading)
            auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                operationSuccessfull = true

            }
            if(operationSuccessfull){
                val userId = auth.currentUser!!.uid
                val dataObject  = User(userName = username, userId = userId, password = password, email = email)
                fireStore.collection(Constants.COLLECTION_NAME_USERS).document().set(dataObject).addOnSuccessListener {

                }.await()

                emit(Response.Success(operationSuccessfull))

            }
            else {
                emit(Response.Success(operationSuccessfull))
            }
        }catch (exception:Exception){
            emit(Response.Error("Error Occured:"+exception.localizedMessage))
        }

    }
}