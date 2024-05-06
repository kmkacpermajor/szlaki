package com.mahor.szlaki

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TrailDao {

    @Query("SELECT * FROM trail_table")
    fun getAllTrails(): Flow<List<Trail>>

    @Query("SELECT * FROM trail_table WHERE uid = :uid")
    fun getTrailById(uid: Long): Flow<Trail>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(trail: Trail)

    @Query("DELETE FROM trail_table")
    suspend fun deleteAll()
}