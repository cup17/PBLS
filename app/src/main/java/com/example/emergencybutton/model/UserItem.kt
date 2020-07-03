package com.example.emergencybutton.model

import com.google.gson.annotations.SerializedName

data class UserItem(

    @field:SerializedName("msg")
    val msg: String? = null,

    @field:SerializedName("data")
    val data: Data? = null,

    @field:SerializedName("status")
    val status: String? = null
)

data class Data(

    @field:SerializedName("number")
    val number: String? = null,

    @field:SerializedName("error_msg")
    val errorMsg: String? = null,

    @field:SerializedName("image")
    val image: String? = null,

    @field:SerializedName("pass")
    val pass: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("email")
    val email: String? = null
)
