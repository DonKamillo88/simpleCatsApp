package org.k2apps.simplecatsapp.data.model

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "cats")
data class Cat(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "url") @Json(name = "url") val imgSrcUrl: String,
    @ColumnInfo(name = "width") val width: Int,
    @ColumnInfo(name = "height") val height: Int,

    @ColumnInfo(name = "breed_id") var bredId: String?,

    @Ignore
    @ColumnInfo(name = "breeds") val breeds: List<Breed?> = emptyList()
) : Parcelable {

    constructor(
        id: String,
        imgSrcUrl: String,
        width: Int,
        height: Int,
        bredId: String?
    ) : this(id, imgSrcUrl, width, height, bredId, emptyList())
}