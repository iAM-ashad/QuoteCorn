package com.app.quotely.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL

@Database(
    entities = [
        QuoteEntity::class,
        TagEntity::class,
        AlbumEntity::class,
        QuoteAlbumCrossRef::class
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
@ConstructedBy(QuotelyDatabaseConstructor::class)
abstract class QuotelyDatabase : RoomDatabase() {
    abstract fun quoteDao(): QuoteDao
    abstract fun tagDao(): TagDao
    abstract fun albumDao(): AlbumDao

    companion object {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(connection: SQLiteConnection) {
                connection.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `albums` (
                        `id` TEXT NOT NULL, 
                        `name` TEXT NOT NULL, 
                        `description` TEXT, 
                        `coverThemeId` TEXT NOT NULL, 
                        `createdAt` INTEGER NOT NULL, 
                        `updatedAt` INTEGER NOT NULL, 
                        PRIMARY KEY(`id`)
                    )
                    """.trimIndent()
                )

                connection.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `quote_album_cross_ref` (
                        `quoteId` TEXT NOT NULL, 
                        `albumId` TEXT NOT NULL, 
                        PRIMARY KEY(`quoteId`, `albumId`), 
                        FOREIGN KEY(`quoteId`) REFERENCES `quotes`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE, 
                        FOREIGN KEY(`albumId`) REFERENCES `albums`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE
                    )
                    """.trimIndent()
                )

                connection.execSQL("CREATE INDEX IF NOT EXISTS `index_quote_album_cross_ref_quoteId` ON `quote_album_cross_ref` (`quoteId`)")
                connection.execSQL("CREATE INDEX IF NOT EXISTS `index_quote_album_cross_ref_albumId` ON `quote_album_cross_ref` (`albumId`)")
            }
        }
    }
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object QuotelyDatabaseConstructor : RoomDatabaseConstructor<QuotelyDatabase> {
    override fun initialize(): QuotelyDatabase
}
