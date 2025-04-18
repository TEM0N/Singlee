package an.imation.singlee.data.mapper

import an.imation.singlee.data.model.PostApiModel
import an.imation.singlee.domain.model.PostDomainModel

class PostDataMapper {
    fun toDomain(apiModel: PostApiModel): PostDomainModel? = runCatching {
        PostDomainModel(
        userId = apiModel.userId?.toInt()!!,
        id = apiModel.id?.toInt()!!,
        title = apiModel.title!!,
        body = apiModel.body!!
    )
    }.getOrElse {
        null
    }
}