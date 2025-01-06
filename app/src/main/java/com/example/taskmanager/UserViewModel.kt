package com.example.taskmanager

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserViewModel(private val repository: UserRepository) : ViewModel() {
    private val _currentUser = MutableLiveData<User?>()
    val currentUser: LiveData<User?> get() = _currentUser

    fun setCurrentUser(user: User) {
        _currentUser.value = user
    }
    fun verifyUser(email: String, password: String, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            val storedPassword = repository.getPassword(email)
            withContext(Dispatchers.Main) {
                callback(storedPassword == password)
            }
        }
    }
    private val _signedInEmail = MutableStateFlow<String?>(null)
    val signedInEmail = _signedInEmail.asStateFlow()

    fun setSignedInEmail(email: String) {
        _signedInEmail.value = email
    }

    fun insertUser(user: User) {
        viewModelScope.launch {
            repository.insertUser(user)
        }
    }

    fun deleteUserByEmail(email: String) {
        viewModelScope.launch {
            repository.deleteUserByEmail(email)
        }
    }

    fun updateUser(user: User){
        viewModelScope.launch {
            repository.updateUser(user)
        }
    }

    fun getPassword(email: String, onResult: (String?) -> Unit) {
        viewModelScope.launch {
            val password = repository.getPassword(email)
            onResult(password)
        }
    }

    suspend fun getName(email: String): String? {
        return repository.getName(email)
    }

    suspend fun getLastname(email: String): String? {
        return repository.getLastname(email)
    }

    suspend fun getAge(email: String): Int? {
        return repository.getAge(email)
    }

    suspend fun getPhoneNumber(email: String): String? {
        return repository.getPhoneNumber(email)
    }

    fun updateName(name: String, email: String) {
        viewModelScope.launch {
            repository.updateName(name, email)
        }
    }

    fun updateLastname(lastname: String, email: String) {
        viewModelScope.launch {
            repository.updateLastname(lastname, email)
        }
    }

    fun updateAge(age: Int, email: String) {
        viewModelScope.launch {
            repository.updateAge(age, email)
        }
    }

    fun updatePhoneNumber(phoneNumber: String, email: String) {
        viewModelScope.launch {
            repository.updatePhoneNumber(phoneNumber, email)
        }
    }



}