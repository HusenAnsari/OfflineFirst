package com.husenansari.offlinefirst.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.husenansari.offlinefirst.repository.GetUserRepository

class UserViewModelProviderFactory(
    val app: Application,
    val getUserRepository: GetUserRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserViewModel(app, getUserRepository) as T
    }
}