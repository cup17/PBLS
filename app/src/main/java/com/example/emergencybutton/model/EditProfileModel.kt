package com.example.emergencybutton.model

import okhttp3.MultipartBody
import java.io.File

class EditProfileModel {

    var id : String = String()
    var nama : String = String()
    var number : String = String()
    var email : String = String()
    var oldEmail : String = String()
    var pass : String = String()
    var fileGambar : MultipartBody.Part? = null

    companion object {
        var model : EditProfileModel = EditProfileModel()

        fun getId(editProfileModel: EditProfileModel): String? {
            return editProfileModel.id
        }

        fun setId(id: String?) {
            model.id = id!!
        }

        fun getNama(editProfileModel: EditProfileModel): String? {
            return editProfileModel.nama
        }

        fun setNama(nama: String?) {
            model.nama = nama!!
        }

        fun getEmail(editProfileModel: EditProfileModel): String? {
            return editProfileModel.email
        }

        fun setEmail(email: String?) {
            model.email = email!!
        }

        fun getNumber(editProfileModel: EditProfileModel): String? {
            return editProfileModel.number
        }

        fun setNumber(number: String?) {
            model.number = number!!
        }

        fun getOldEmail(editProfileModel: EditProfileModel): String? {
            return editProfileModel.oldEmail
        }

        fun setOldEmail(oldEmail: String?) {
            model.oldEmail = oldEmail!!
        }

        fun getPassword(editProfileModel: EditProfileModel): String? {
            return editProfileModel.pass
        }

        fun setPassword(pass: String?) {
            model.pass = pass!!
        }

        fun getFileGambar(editProfileModel: EditProfileModel): MultipartBody.Part? {
            return editProfileModel.fileGambar
        }

        fun setFileGambar(fileGambar: MultipartBody.Part?) {
            model.fileGambar = fileGambar!!
        }
    }
}