package uz.rdo.projects.xabarchichat.di.modules

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import uz.rdo.projects.xabarchichat.data.repositories.*
import uz.rdo.projects.xabarchichat.ui.screen.activities.entry.EntryRepositoryImpl
import uz.rdo.projects.xabarchichat.ui.screen.activities.main.MainRepositoryImpl
import uz.rdo.projects.xabarchichat.ui.screen.entryFragments.signIn.SignInRepositoryImpl
import uz.rdo.projects.xabarchichat.ui.screen.entryFragments.signUp.SignUpRepositoryImpl
import uz.rdo.projects.xabarchichat.ui.screen.mainFragments.allUsersFragment.AllUsersRepositoryImpl
import uz.rdo.projects.xabarchichat.ui.screen.mainFragments.contacts.ContactsRepositoryImpl
import uz.rdo.projects.xabarchichat.ui.screen.mainFragments.dualMessage.DualMessageRepositoryImpl
import uz.rdo.projects.xabarchichat.ui.screen.mainFragments.settings.SettingsRepositoryImpl
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun getMainActivityRepository(repository: MainRepositoryImpl): MainRepository

    @Binds
    @Singleton
    fun getEntryActivityRepository(repository: EntryRepositoryImpl): EntryRepository

    @Binds
    @Singleton
    fun getSignUpRepository(repository: SignUpRepositoryImpl): SignUpRepository

    @Binds
    @Singleton
    fun getSignInRepository(repository: SignInRepositoryImpl): SignInRepository

    @Binds
    @Singleton
    fun getContactsRepository(repository: ContactsRepositoryImpl): ContactsRepository

    @Binds
    @Singleton
    fun getAllUsersRepository(repository: AllUsersRepositoryImpl): AllUsersRepository

    @Binds
    @Singleton
    fun getDualMessageRepository(repository: DualMessageRepositoryImpl): DualMessageRepository

    @Binds
    @Singleton
    fun getSettingsRepository(repository: SettingsRepositoryImpl): SettingsRepository


}