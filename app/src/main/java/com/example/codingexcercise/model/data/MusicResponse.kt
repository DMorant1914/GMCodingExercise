package com.example.codingexcercise.model.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize as Parcelize

@Parcelize
data class MusicResponse (
    val results: List<MusicItem>
): Parcelable

@Parcelize
data class MusicItem(
    val artistName: String,
    val trackName: String,
    val artworkUrl100: String,
    val trackPrice: Double,
    val primaryGenreName:String,
    val releaseDate:String,
    val previewUrl: String
): Parcelable


