package br.com.fiap.roomdatabase.service

import br.com.fiap.roomdatabase.task.Task
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TaskService {

    @GET("tasks")
    suspend fun getTasks() : List<Task>

    @POST("new-task")
    suspend fun newTask(@Body task: Task)
}


