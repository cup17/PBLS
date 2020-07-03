package com.example.emergencybutton.fragment.profile

import android.app.AlertDialog
import com.example.emergencybutton.model.EditProfileModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

interface ProfileConstruct {
    interface View{
        fun showDataUser()
        fun setFotoProfile()
        fun clearLoginData(): AlertDialog?
        fun goToLogin()
        fun uploadPhotoSucces(photo: String?)
        fun onFailure(msg: String)
        fun onSuccess(msg: String)
    }
    interface Presenter{
        fun pushUserData(model : EditProfileModel, companion : EditProfileModel.Companion)
        fun uploadUserImage(email : RequestBody, picture : MultipartBody.Part)
    }
}