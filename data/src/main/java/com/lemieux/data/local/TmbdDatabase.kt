package com.lemieux.data.local


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lemieux.data.local.model.MovieEntity

@Database(
    entities = [MovieEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class TmbdDatabase : RoomDatabase() {
    abstract val movieDao: MovieDao

    companion object {
        @Volatile
        private var instance: TmbdDatabase? = null

        fun getInstance(context: Context): TmbdDatabase =
            instance ?: synchronized(this) {
                instance
                    ?: buildDatabase(context).also { instance = it }
            }

        private fun buildDatabase(context: Context) =
            Room
                .databaseBuilder(
                    context.applicationContext,
                    TmbdDatabase::class.java,
                    "tmbd.db",
                ).build()
    }
}
