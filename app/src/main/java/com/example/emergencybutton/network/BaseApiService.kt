package com.example.emergencybutton.network

import android.util.TypedValue
import com.example.emergencybutton.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import java.io.File

interface BaseApiService {
    
    @FormUrlEncoded
    @POST("user_login.php")
    fun loginRequest(
        @Field("email") email: String?,
        @Field("pass") password: String?
    ): Call<UserItem>

    @FormUrlEncoded
    @POST("user_forpass.php")
    fun forpassRequest(
        @Field("email") email: String?
    ): Call<ForPassItem>

    @FormUrlEncoded
    @POST("user_register.php")
    fun registerRequest(
        @Field("name") nama: String?,
        @Field("number") noHp: String?,
        @Field("email") email: String?,
        @Field("pass") password: String?
    ): Call<UserItem>

    @FormUrlEncoded
    @POST("user_update.php")
    fun updateRequest(
        @Field("name") nama: String?,
        @Field("number") noHp: String?,
        @Field("email") email: String?,
        @Field("old_email") oldEmail: String?,
        @Field("pass") password: String?
    ): Call<UserItem>

    @Multipart
    @POST("things_insert_lost.php")
    fun insertLostThingsRequest(
        @Part("role_type") roleType: RequestBody?,
        @Part("name") name: RequestBody?,
        @Part("posters_name") postersName: RequestBody?,
        @Part("number") number: RequestBody?,
        @Part("date") date: RequestBody?,
        @Part("location") location: RequestBody?,
        @Part("description") description: RequestBody?,
        @Part image : MultipartBody.Part
    ): Call<LostItem>

    @Multipart
    @POST("things_insert_found.php")
    fun insertFoundThingsRequest(
        @Part("role_type") roleType: RequestBody?,
        @Part("name") name: RequestBody?,
        @Part("posters_name") postersName: RequestBody?,
        @Part("number") number: RequestBody?,
        @Part("date") date: RequestBody?,
        @Part("location") location: RequestBody?,
        @Part("description") description: RequestBody?,
        @Part image : MultipartBody.Part
    ): Call<LostItem>

    @GET("things_lost_get_all.php")
    fun getAllLostThings() : Call<ResponseNotification>

    @FormUrlEncoded
    @POST("emergency_get_all.php")
    fun getAllEmergencies(
        @Field("lat") lat: String?,
        @Field("lng") lng: String?
    ) : io.reactivex.Observable<List<EmergencyItem>>

    @FormUrlEncoded
    @POST("emergency_insert.php")
    fun insertEmergencyRequest(
        @Field("name") name: String?,
        @Field("lat") lat: String?,
        @Field("lng") lng: String?
    ): Call<EmergencyItem>

    @FormUrlEncoded
    @POST("emergency_delete.php")
    fun deleteEmergencyRequest(
        @Field("name") name: String?
    ): Call<EmergencyItem>

    @Multipart
    @POST("upload.php")
    fun uploadUserPhoto(
        @Part("email") email: RequestBody?,
        @Part picture : MultipartBody.Part?
    ): Call<EditProfileModel>

//    @POST("picture/profiles/{path}")
//    fun imageProfileRequest(
//        @Path("path") image : String?
//    ): Call<UserItem>
}