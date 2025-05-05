package an.imation.singlee.data.db

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.DeleteColumn
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [FavoritePostDBModel::class],
    version = 6,
    exportSchema = true,
    autoMigrations = [
        AutoMigration (from = 1, to = 2),
        AutoMigration(from = 2, to = 3),
        AutoMigration(from = 3, to = 4, spec = Migration_3_4::class)
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoritePostsDao(): FavoritePostsDao
}
@DeleteColumn(tableName = "new_table", columnName = "fieldA")
class Migration_3_4 : AutoMigrationSpec

val MIGRATION_4_5 = object : Migration(4, 5) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE new_table RENAME COLUMN fieldB TO fieldW")
    }
}

val MIGRATION_5_6 = object : Migration(5, 6) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("DROP TABLE IF EXISTS new_table")
    }
}