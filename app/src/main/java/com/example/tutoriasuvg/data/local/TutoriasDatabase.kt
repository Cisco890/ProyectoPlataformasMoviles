package com.example.tutoriasuvg.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tutoriasuvg.data.local.dao.UserDao
import com.example.tutoriasuvg.data.local.entity.User

@Database(entities = [User::class], version = 1)
abstract class TutoriasDatabase : RoomDatabase(){

    abstract fun userDao(): UserDao

    companion object{
        @Volatile
        private var INSTANCE: TutoriasDatabase? = null

        fun getDatabase(context: Context): TutoriasDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TutoriasDatabase::class.java,
                    "tutorias_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}