package com.example.emergencybutton.model

import com.google.gson.annotations.SerializedName

data class LostItem(

	@field:SerializedName("msg")
	val msg: String? = null,

	@field:SerializedName("data")
	val data: DataLost? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class DataLost(

	@field:SerializedName("msg")
	val msg: String? = null,

	@field:SerializedName("url_picture")
	val urlPicture: String? = null
)
