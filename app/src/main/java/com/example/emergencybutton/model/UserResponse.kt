package com.example.emergencybutton.model

import com.google.gson.annotations.SerializedName

data class UserResponse(

	@field:SerializedName("user")
	val user: List<UserItem>? = null

)
