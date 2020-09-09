package br.com.fiap.roomdatabase.task

import android.os.SystemClock
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.roomdatabase.database.TaskDao
import br.com.fiap.roomdatabase.service.TaskService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel(
    private val service: TaskService,
    private val taskDao: TaskDao
): ViewModel() {

    val tasksLiveData = MutableLiveData<List<Task>>()
    val newTaskLiveData = MutableLiveData<Task>()

    init {
        fetchTasks()
    }

    private fun fetchTasks() {
        viewModelScope.launch(Dispatchers.IO) {

            //buscar do banco local
            val cachedData = taskDao.getTasks()
            tasksLiveData.postValue(cachedData)

            //buscar dados da API
            SystemClock.sleep(2000)
            val result: List<Task> = service.getTasks()

            val result2 = mutableListOf<Task>()
            result2.addAll(result)
            result2.add(Task(description = "NOVA TASK TESTE"))
            tasksLiveData.postValue(result2)

            //atualizar o cache
            taskDao.deleteAll()
            taskDao.insertTask(*result.toTypedArray())
        }
    }

    fun newTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            service.newTask(task)
            newTaskLiveData.postValue(task)
        }
    }

}