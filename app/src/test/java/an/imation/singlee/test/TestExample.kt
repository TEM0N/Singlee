package an.imation.singlee.test

import an.imation.singlee.BaseTestClass
import an.imation.singlee.domain.usecase.FetchCommentsUseCase
import an.imation.singlee.domain.usecase.FetchPostsUseCase
import an.imation.singlee.mock.IPostApiMock
import an.imation.singlee.presentation.event.posts.PostsIntent
import an.imation.singlee.presentation.viewmodel.PostsViewModel
import kotlinx.coroutines.delay
import org.junit.Test
import org.junit.Assert.*
import org.koin.test.inject


class TestExample: BaseTestClass() {
    @Test
    fun postSizeTest(){
        test {
            val posts = inject<FetchPostsUseCase>().value.invoke().data!!
            assertEquals(posts.size, IPostApiMock.initPosts.size)
        }
    }
    @Test
    fun commentTest(){
        test {
            val posts = inject<FetchPostsUseCase>().value.invoke().data!!

            posts.forEach { post ->
                val comments = inject<FetchCommentsUseCase>().value.invoke(post.id).data!!

                assertEquals(comments.size, IPostApiMock.initComments.size)

                comments.forEach { comment ->
                    assertNotNull(comment.id)
                    assertNotNull(comment.body)
                    assertNotNull(comment.email)
                    assertNotNull(comment.name)
                    assertEquals(post.id, comment.postId)
                }

                val commentIds = comments.map { it.id }
                assertEquals(commentIds.toSet().size, comments.size)
            }
        }
    }

   @Test
    fun viewModelLoadTest(){
        test {
            val vm = inject<PostsViewModel>().value

            vm.sendIntent(PostsIntent.LoadPosts)

            val state = vm.state.value

            assertFalse(state.isLoading)
            assertTrue(state.posts.isNotEmpty())
            assertNull(state.error)

        }
    }

    @Test
    fun searchTest() {
        test {
            val vm = inject<PostsViewModel>().value

            vm.sendIntent(PostsIntent.LoadPosts)
            delay(400)

            val originalPosts = vm.state.value.posts
            val query = originalPosts.first().title.take(3)

            vm.sendIntent(PostsIntent.SearchPosts(query))
            delay(100)

            val filtered = vm.state.value.filteredPosts
            assertTrue(filtered.all {
                it.title.contains(query, true) || it.body.contains(query, true)
            })
        }
    }
}