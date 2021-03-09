package uz.rdo.projects.xabarchichat.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import uz.rdo.projects.xabarchichat.app.App
import javax.inject.Singleton

/**
 *  Created by Davronbek Raximjanov 17-Feb-2021
 **/

@Module
@InstallIn(ApplicationComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun getApp(): Context = App.instance
}