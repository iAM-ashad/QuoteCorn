package com.app.quotely.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters

@Database(
    entities = [QuoteEntity::class, TagEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
@ConstructedBy(QuotelyDatabaseConstructor::class)
abstract class QuotelyDatabase : RoomDatabase() {
    abstract fun quoteDao(): QuoteDao
    abstract fun tagDao(): TagDao
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object QuotelyDatabaseConstructor : RoomDatabaseConstructor<QuotelyDatabase> {
    override fun initialize(): QuotelyDatabase
}
