package an.imation.singlee.data.mapper

import an.imation.singlee.data.model.CommentApiModel
import an.imation.singlee.domain.model.CommentDomainModel

class CommentDataMapper {
    fun toDomain(apiModel: CommentApiModel): CommentDomainModel = CommentDomainModel(
        postId = apiModel.postId,
        id = apiModel.id,
        name = apiModel.name,
        email = apiModel.email,
        body = apiModel.body
    )
}