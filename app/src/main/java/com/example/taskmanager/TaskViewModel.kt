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

    private val _totalTasks = MutableStateFlow(0)
    val totalTasks: StateFlow<Int> = _totalTasks

    private val _completedTasks = MutableStateFlow(0)
    val completedTasks: StateFlow<Int> = _completedTasks

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

    fun fetchTaskCounts(userEmail: String) {
        viewModelScope.launch {
            repository.getTotalTasksCount(userEmail).collect { count ->
                _totalTasks.value = count
            }
        }

        viewModelScope.launch {
            repository.getCompletedTasksCount(userEmail).collect { count ->
                _completedTasks.value = count
            }
        }
    }

    fun getTotalTasksCount(email:String){
        viewModelScope.launch (Dispatchers.IO){
            repository.getTotalTasksCount(email)
            loadTasks(email)
        }
    }

    fun getCompletedTasksCount(email:String){
        viewModelScope.launch (Dispatchers.IO){
            repository.getCompletedTasksCount(email)
            loadTasks(email)
        }
    }
}