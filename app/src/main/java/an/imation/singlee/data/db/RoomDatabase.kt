package an.imation.singlee.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [FavoritePostEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoritePostsDao(): FavoritePostsDao
}