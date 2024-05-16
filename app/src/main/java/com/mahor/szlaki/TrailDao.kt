package com.mahor.szlaki

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TrailDao {

    @Query("SELECT * FROM trail_table")
    fun getAllTrails(): Flow<List<Trail>>

    @Query("SELECT * FROM trail_table WHERE difficulty = :difficulty")
    fun getDifficultyTrails(difficulty: String): Flow<List<Trail>>

    @Query("SELECT * FROM trail_table WHERE id=:id")
    fun getTrailById(id: Long): Flow<Trail>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(trail: Trail)

    @Query("DELETE FROM trail_table")
    suspend fun deleteAll()

    @Update
    suspend fun update(trail: Trail)
}