package an.imation.singlee.data.api

import an.imation.singlee.data.model.PostApiModel
import retrofit2.http.GET

interface IPostApi {
    @GET("posts")
    suspend fun getPosts(): List<PostApiModel>
}