package an.imation.singlee.data.model

data class CommentApiModel(
    val postId: Int,
    val id: Int,
    val name: String,
    val email: String,
    val body: String
)