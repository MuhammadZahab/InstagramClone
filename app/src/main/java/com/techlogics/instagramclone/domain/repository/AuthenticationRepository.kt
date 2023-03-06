package com.techlogics.instagramclone.domain.repository

import com.techlogics.instagramclone.util.Response
import kotlinx.coroutines.flow.Flow

interface AuthenticationRepository {

    fun isUserAuthenticatedInFirebase():Boolean
    fun getFirebaseAuthState(): Flow<Boolean>
    fun firebaseSignIn(email:String,password:String):Flow<Response<Boolean>>
    fun firebaseSignOut():Flow<Response<Boolean>>
    fun firebaseSignUp(email:String,password:String,username:String):Flow<Response<Boolean>>

}