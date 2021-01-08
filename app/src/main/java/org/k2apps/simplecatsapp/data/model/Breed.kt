package org.k2apps.simplecatsapp.data.model

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "breeds")
data class Breed(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "temperament") val temperament: String,
    @ColumnInfo(name = "origin") val origin: String,
    @ColumnInfo(name = "description") val description: String
) : Parcelable