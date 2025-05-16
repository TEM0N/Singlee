package an.imation.singlee.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PostDomainModel(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
): Parcelable