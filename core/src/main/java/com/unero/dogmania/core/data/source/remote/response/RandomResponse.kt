package com.unero.dogmania.core.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RandomResponse(
	@field:SerializedName("message")
	val images: List<String>,

	@field:SerializedName("status")
	val status: String
) : Parcelable
