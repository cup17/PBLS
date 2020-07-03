package com.example.emergencybutton.fragment.profile

import android.util.Log
import com.example.emergencybutton.model.EditProfileModel
import com.example.emergencybutton.model.UserItem
import com.example.emergencybutton.network.BaseApiService
import com.example.emergencybutton.network.UtilsApi
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ProfilePresenter(val view: ProfileConstruct.View) : ProfileConstruct.Presenter {

    var mApiService: BaseApiService = UtilsApi.getAPIService()!!

    override fun pushUserData(model: EditProfileModel, companion: EditProfileModel.Companion) {
        mApiService.updateRequest(companion.getNama(model), companion.getNumber(model), companion.getEmail(model), companion.getOldEmail(model), companion.getPassword(model))
            .enqueue(object : Callback<UserItem> {
                override fun onFailure(call: Call<UserItem>, t: Throwable) {
                    view.onFailure(t.localizedMessage)
                }

                override fun onResponse(call: Call<UserItem>, response: Response<UserItem>) {
                    view.onSuccess(response.message())
                }
            })
    }

    override fun uploadUserImage(email : RequestBody, picture : MultipartBody.Part) {
        mApiService.uploadUserPhoto(email, picture)
            .enqueue(object : Callback<EditProfileModel>{
                override fun onFailure(call: Call<EditProfileModel>, t: Throwable) {
                    Log.d("UploadFoto", t.localizedMessage)
                    Log.d("UploadFoto", t.message)
                }

                override fun onResponse(call: Call<EditProfileModel>, response: Response<EditProfileModel>) {
                    view.uploadPhotoSucces("Photo selesai di upload!")
                    Log.d("UploadFoto", "BERHASIL")
                    Log.d("UploadFoto", response.toString())
                }
            })
    }
}