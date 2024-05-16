package com.mahor.szlaki

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Trail::class], version = 1)
abstract class TrailRoomDatabase : RoomDatabase() {

    abstract fun trailDao(): TrailDao

    companion object {
        @Volatile
        private var INSTANCE: TrailRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): TrailRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TrailRoomDatabase::class.java,
                    "trail_database"
                )
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    // Migration is not part of this codelab.
                    .fallbackToDestructiveMigration()
                    .addCallback(TrailDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

        private class TrailDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            /**
             * Override the onCreate method to populate the database.
             */
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                // If you want to keep the data through app restarts,
                // comment out the following line.
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.trailDao())
                    }
                }
            }
        }

        /**
         * Populate the database in a new coroutine.
         * If you want to start with more trails, just add them.
         */
        suspend fun populateDatabase(trailDao: TrailDao) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
            trailDao.deleteAll()

            var trail = Trail(0, "Szlak Górski", "Wspinaczka na górę z niesamowitym widokiem na dolinę.", "https://images.unsplash.com/photo-1454496522488-7a8e488e8606?q=80&w=2076&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D", 120, "hard")
            trailDao.insert(trail)
            trail = Trail(1, "Ścieżka Leśna", "Przyjemne spacerowanie wśród drzew i ptaków.", "https://images.unsplash.com/photo-1462143338528-eca9936a4d09?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D", 60, "easy")
            trailDao.insert(trail)
            trail = Trail(2, "Trasa Nadmorska", "Spacer wzdłuż wybrzeża z widokiem na morze.", "https://plus.unsplash.com/premium_photo-1705491760501-e4dad40a1287?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D", 90, "medium")
            trailDao.insert(trail)
            trail = Trail(3, "Szlak Wodny", "Przeprawa kajakiem przez malownicze jeziora.", "https://images.unsplash.com/photo-1557456170-0cf4f4d0d362?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D", 180, "hard")
            trailDao.insert(trail)
            trail = Trail(4, "Szlak Historyczny", "Wędrówka po śladach starożytnych ruin.", "https://images.unsplash.com/photo-1603566541830-972ff1b4b2cd?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D", 150, "medium")
            trailDao.insert(trail)
            trail = Trail(5, "Ścieżka Kwiatowa", "Spacer po polach pełnych kolorowych kwiatów.", "https://images.unsplash.com/photo-1589387001998-2817d7ea5eee?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D", 45, "easy")
            trailDao.insert(trail)
            trail = Trail(6, "Trasa Górnicza", "Odkryj tajemnice dawnych kopalni węgla.", "https://images.unsplash.com/photo-1676134711864-97da91e82884?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D", 100, "medium")
            trailDao.insert(trail)
            trail = Trail(7, "Szlak Przyrodniczy", "Nauka o lokalnych gatunkach roślin i zwierząt.", "https://images.unsplash.com/photo-1433086966358-54859d0ed716?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D", 75, "easy")
            trailDao.insert(trail)
            trail = Trail(8, "Ścieżka Zdrowia", "Wypoczynek i jogging na świeżym powietrzu.", "https://images.unsplash.com/photo-1426604966848-d7adac402bff?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D", 30, "easy")
            trailDao.insert(trail)
            trail = Trail(9, "Trasa Wina", "Degustacja wina w pięknych winnicach.", "https://images.unsplash.com/photo-1596142332133-327e2a0ff006?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D", 240, "medium")
            trailDao.insert(trail)
        }
    }
}
