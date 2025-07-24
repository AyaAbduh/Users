package com.example.users.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.users.data.User
import com.example.users.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    val users = repository.getAllUsers()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _userAdded = MutableStateFlow(false)
    val userAdded: StateFlow<Boolean> = _userAdded

    fun insertUser(user: User) {
        viewModelScope.launch {
            repository.insertUser(user)
            _userAdded.value = true
        }
    }

    fun resetUserAddedFlag() {
        _userAdded.value = false
    }
}
