package an.imation.singlee.data.mapper

import an.imation.singlee.data.model.CommentApiModel
import an.imation.singlee.domain.model.CommentDomainModel

class CommentDataMapper {
    fun toDomain(apiModel: CommentApiModel): CommentDomainModel? = runCatching {
        CommentDomainModel(
            postId = apiModel.postId?.toInt()!!,
            id = apiModel.id?.toInt()!!,
            name = apiModel.name!!,
            email = apiModel.email!!,
            body = apiModel.body!!
        )
    }.getOrElse {
        null
    }
}
