package com.mahor.szlaki

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trail_table")
data class Trail(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "photo_url") val photoUrl: String,
    @ColumnInfo(name = "walk_minutes") val walk_minutes: Int,
    @ColumnInfo(name = "difficulty") val difficulty: String,
) {
    fun getLocalizedDifficulty(context: Context): String {
        // Retrieve the localized difficulty string using the difficulty key
        return context.getString(
            context.resources.getIdentifier(
                difficulty,
                "string",
                context.packageName
            )
        )
    }
}
