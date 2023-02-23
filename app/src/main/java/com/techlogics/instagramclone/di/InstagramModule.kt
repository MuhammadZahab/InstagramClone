package com.techlogics.instagramclone.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object InstagramModule {


    @Provides
    @Singleton
    fun provideFirebaseAuthentication(): FirebaseAuth {

        return FirebaseAuth.getInstance()
    }

    @Singleton
    @Provides
    fun provideFirebbaseFireStore():FirebaseFirestore{

        return FirebaseFirestore.getInstance()
    }

    @Singleton
    @Provides
    fun provideFirebaseStorage():FirebaseStorage{

        return FirebaseStorage.getInstance()
    }
}