package com.example.emergencybutton.model

import com.google.gson.annotations.SerializedName

data class ResponseNotification(

	@field:SerializedName("msg")
	val msg: String? = null,

	@field:SerializedName("data")
	val data: List<NotificationItem?>? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class NotificationItem(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("number")
	val number: String? = null,

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("role_type")
	val roleType: String? = null,

	@field:SerializedName("role_id")
	val roleId: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("posters_name")
	val postersName: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("location")
	val location: String? = null,

	@field:SerializedName("id_things")
	val idThings: String? = null
)
