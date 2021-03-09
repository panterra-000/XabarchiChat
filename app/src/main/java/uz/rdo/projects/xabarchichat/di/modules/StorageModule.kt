package uz.rdo.projects.xabarchichat.di.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import uz.rdo.projects.xabarchichat.data.localStorage.LocalStorage
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class StorageModule {

    @Provides
    @Singleton
    fun getLocalStorage(): LocalStorage = LocalStorage.instance
}