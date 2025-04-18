package an.imation.singlee.data.model

import com.google.gson.annotations.SerializedName

data class CommentApiModel(
    @SerializedName("postId")
    val postId: String?,
    val id: String?,
    val name: String?,
    val email: String?,
    val body: String?
)