package an.imation.singlee.data.api

import an.imation.singlee.data.model.CommentApiModel
import an.imation.singlee.data.model.PostApiModel
import com.andretietz.retrofit.ResponseCache
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface IPostApi {
    @GET("posts")
    @ResponseCache(2,TimeUnit.MINUTES)
    suspend fun getPosts(): List<PostApiModel>

    @GET("comments")
    @ResponseCache(2,TimeUnit.MINUTES)
    suspend fun getCommentsByPostId(@Query("postId") postId: Int): List<CommentApiModel>
}