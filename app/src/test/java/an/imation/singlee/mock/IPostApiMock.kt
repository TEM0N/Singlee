package an.imation.singlee.mock

import an.imation.singlee.data.api.IPostApi
import an.imation.singlee.data.model.CommentApiModel
import an.imation.singlee.data.model.PostApiModel
import org.koin.dsl.module

class IPostApiMock {

    companion object {
        val initPosts = (0..10).map {
            PostApiModel(
                body = it.toString(),
                id = it.toString(),
                title = it.toString(),
                userId = it.toString()
            )
        }

        val initComments = (0..5).map {
            CommentApiModel(
                body = it.toString(),
                email = it.toString(),
                id = it.toString(),
                name = it.toString(),
                postId = it.toString()
            )
        }
    }

    fun mModule(
        loadPosts: () -> List<PostApiModel> = { initPosts }
    ) = module {
        single<IPostApi> {
            object : IPostApi {
                override suspend fun getPosts() = loadPosts.invoke()
                override suspend fun getCommentsByPostId(postId: Int): List<CommentApiModel> {
                    return initComments.map { it.copy(postId = postId.toString()) }
                }
            }
        }
    }
}