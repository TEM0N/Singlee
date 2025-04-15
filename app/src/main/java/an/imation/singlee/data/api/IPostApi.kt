package an.imation.singlee.data.api

import an.imation.singlee.data.model.CommentApiModel
import an.imation.singlee.data.model.PostApiModel
import retrofit2.http.GET
import retrofit2.http.Query

interface IPostApi {
    @GET("posts")
    suspend fun getPosts(): List<PostApiModel>

    @GET("comments")
    suspend fun getCommentsByPostId(@Query("postId") postId: Int): List<CommentApiModel>
}