package an.imation.singlee.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritePostsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(post: FavoritePostDBModel)

    @Delete
    suspend fun removeFavorite(post: FavoritePostDBModel)

    @Query("SELECT * FROM favorite_posts ORDER BY timestamp DESC")
    fun getAllFavorites(): Flow<List<FavoritePostDBModel>>

    @Query("SELECT EXISTS(SELECT * FROM favorite_posts WHERE postId = :postId)")
    suspend fun isFavorite(postId: Int): Boolean
}