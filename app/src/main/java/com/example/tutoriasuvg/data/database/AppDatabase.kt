package com.example.tutoriasuvg.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.tutoriasuvg.data.database.dao.SolicitudDao
import com.example.tutoriasuvg.data.database.dao.TutorDao
import com.example.tutoriasuvg.data.database.dao.TutoriaAsignadaDao
import com.example.tutoriasuvg.data.model.Solicitud
import com.example.tutoriasuvg.data.model.Tutor
import com.example.tutoriasuvg.data.model.TutoriaAsignada

@Database(
    entities = [Solicitud::class, Tutor::class, TutoriaAsignada::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun solicitudDao(): SolicitudDao
    abstract fun tutorDao(): TutorDao
    abstract fun tutoriaAsignadaDao(): TutoriaAsignadaDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: android.content.Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = androidx.room.Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "tutorias_uvg_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
