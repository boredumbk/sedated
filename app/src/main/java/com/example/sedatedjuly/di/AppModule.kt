package com.example.sedatedjuly.di

import androidx.room.Room
import com.example.sedatedjuly.feature_todo.data.data_source.TodoDatabase
import com.example.sedatedjuly.feature_todo.data.repository.TodoRepositoryImpl
import com.example.sedatedjuly.feature_todo.domain.repository.TodoRepository
import com.example.sedatedjuly.feature_todo.domain.use_case.AddTodoUseCase
import com.example.sedatedjuly.feature_todo.domain.use_case.DeleteTodoUseCase
import com.example.sedatedjuly.feature_todo.domain.use_case.GetTodosUseCase
import com.example.sedatedjuly.feature_todo.domain.use_case.TodoUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTodoDatabase(app: Appication): TodoDatabase {
        return Room.databaseBuilder(
            app,
            TodoDatabase::class.java,
            TodoDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun providesTodoRepository(db: TodoDatabase): TodoRepository {
        return TodoRepositoryImpl(db.todoDao)
    }

    @Provides
    @Singleton
    fun provideTodoUseCases(repository: TodoRepository): TodoUseCases {
        return TodoUseCases(
            getTodosUseCase = GetTodosUseCase(repository),
            deleteTodoUseCase = DeleteTodoUseCase(repository),
            addTodoUseCase = AddTodoUseCase(repository)
        )
    }
}