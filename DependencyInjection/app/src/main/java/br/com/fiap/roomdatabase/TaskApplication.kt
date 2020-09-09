package br.com.fiap.roomdatabase

import android.app.Application
import android.content.Context
import androidx.room.Room
import br.com.fiap.roomdatabase.database.TaskDao
import br.com.fiap.roomdatabase.database.TaskDatabase
import br.com.fiap.roomdatabase.service.TaskService
import br.com.fiap.roomdatabase.task.TaskViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TaskApplication: Application() {

    private val taskModule = module {
        single { provideRetrofit() }
        single { provideTaskService(get()) }

        single { provideDatabase(get()) }
        single { provideTaskDao(get()) }

        viewModel { TaskViewModel(get(), get()) }
    }

    private fun provideTaskDao(database: TaskDatabase) : TaskDao {
        return database.taskDao()
    }

    private fun provideDatabase(context: Context): TaskDatabase {
        return Room.databaseBuilder(context, TaskDatabase::class.java, "taskdatabase")
                .build()
    }

    private fun provideTaskService(retrofit: Retrofit) : TaskService {
        return retrofit.create(TaskService::class.java)
    }

    private fun provideRetrofit() : Retrofit {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TaskApplication)
            modules(taskModule)
        }
    }
}