package an.imation.singlee.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_posts")
data class FavoritePostDBModel(
    @PrimaryKey val postId: Int,
    val timestamp: Long = System.currentTimeMillis()
)