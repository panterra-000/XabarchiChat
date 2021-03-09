package uz.rdo.projects.xabarchichat.ui.screen.activities.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import uz.rdo.projects.xabarchichat.data.repositories.MainRepository

class MainViewModel @ViewModelInject constructor(
    private val repository: MainRepository
) : ViewModel() {
}