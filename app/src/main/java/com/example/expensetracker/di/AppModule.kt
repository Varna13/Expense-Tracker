package com.example.expensetracker.di

import android.content.Context
import androidx.room.Room
import com.example.expensetracker.data.local.ExpenseDao
import com.example.expensetracker.data.local.ExpenseDatabase
import com.example.expensetracker.data.repository.ExpenseRepositoryImpl
import com.example.expensetracker.domain.repository.ExpenseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): ExpenseDatabase{
        return Room.databaseBuilder(
            context,
            ExpenseDatabase::class.java,
            "expense_db"
        ).build()
    }

    @Provides
    fun provideDao(db: ExpenseDatabase) = db.expenseDao()

    @Provides
    fun provideRepository(dao: ExpenseDao): ExpenseRepository {
        return ExpenseRepositoryImpl(dao)
    }
}
