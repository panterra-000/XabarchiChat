package uz.rdo.projects.xabarchichat.di.modules

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
class FireBaseModule {
    private val database = FirebaseDatabase.getInstance()
    private val dbRootRef = database.reference
    private val userNode = dbRootRef.child("Users")

    @Provides
    @Singleton
    fun getFireAuthModule(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun getFireBaseModule(): FirebaseDatabase {
        return this.database
    }

}