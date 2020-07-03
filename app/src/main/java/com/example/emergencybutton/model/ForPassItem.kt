package com.example.emergencybutton.model

import com.google.gson.annotations.SerializedName

data class ForPassItem(

	@field:SerializedName("msg")
	val msg: String? = null,

	@field:SerializedName("data")
	val data: DataForpass? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class DataForpass(

	@field:SerializedName("pass")
	val pass: String? = null
)
