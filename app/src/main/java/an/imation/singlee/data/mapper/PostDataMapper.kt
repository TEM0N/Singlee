package an.imation.singlee.data.mapper

import an.imation.singlee.data.model.PostApiModel
import an.imation.singlee.domain.model.PostDomainModel

class PostDataMapper {
    fun toDomain(apiModel: PostApiModel): PostDomainModel = PostDomainModel(
        userId = apiModel.userId,
        id = apiModel.id,
        title = apiModel.title,
        body = apiModel.body
    )
}