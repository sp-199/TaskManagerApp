package com.example.taskmanager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> get() = _tasks

    fun addTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertTask(task)
            loadTasks(task.email)
        }
    }

    fun loadTasks(email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val taskList = repository.getTasksByEmail(email)
            _tasks.value = taskList
        }
    }

    fun deleteALLTasks(email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllTasks(email)
        }
    }

    fun deleteTask(taskId: Int, email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTask(taskId)
            loadTasks(email)
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTask(task)
            loadTasks(task.email)
        }
    }
}