package com.example.emergencybutton.fragment.profile

import android.Manifest
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.emergencybutton.R
import com.example.emergencybutton.activity.login.LoginActivity
import com.example.emergencybutton.model.EditProfileModel
import com.example.emergencybutton.model.EditProfileModel.Companion.model
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment(), ProfileConstruct.View {

    lateinit var myPref: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    lateinit var loginEditor: SharedPreferences.Editor

    var presenter: ProfilePresenter = ProfilePresenter(this)

    private val RC_CAMERA = 1
    val REQUEST_TAKE_PHOTO = 1
    val REQUEST_CHOOSE_PHOTO = 2

    lateinit var file: File

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myPref = context!!.getSharedPreferences("userInfo", Context.MODE_PRIVATE)
        editor = context!!.getSharedPreferences("userInfo", Context.MODE_PRIVATE).edit()
        loginEditor = context!!.getSharedPreferences("login", Context.MODE_PRIVATE).edit()

        showDataUser()
        setFotoProfile()

        view.btn_logout.setOnClickListener {
            clearLoginData()
        }

        view.cmv_profile.setOnClickListener {
            checkCameraPermission()
            cropImageAutoSelection()
        }

        view.btn_edit_profile.setOnClickListener {

            var requestFile : RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
            var body : MultipartBody.Part = MultipartBody.Part.createFormData("picture", file.path, requestFile)
            var email : RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), myPref.getString("email", ""))

            if (file == null) {
                var modelCompanion : EditProfileModel.Companion = EditProfileModel
                modelCompanion.setEmail(edt_profile_email.text.toString())
                modelCompanion.setNama(edt_profile_nama.text.toString())
                modelCompanion.setPassword(edt_profile_pass.text.toString())
                modelCompanion.setNumber(edt_profile_number.text.toString())
                modelCompanion.setOldEmail(myPref.getString("email", ""))

                presenter.pushUserData(model, modelCompanion)
            } else {
                var modelCompanion : EditProfileModel.Companion = EditProfileModel
                modelCompanion.setEmail(edt_profile_email.text.toString())
                modelCompanion.setNama(edt_profile_nama.text.toString())
                modelCompanion.setPassword(edt_profile_pass.text.toString())
                modelCompanion.setNumber(edt_profile_number.text.toString())
                modelCompanion.setOldEmail(myPref.getString("email", ""))

                presenter.pushUserData(model, modelCompanion)
                presenter.uploadUserImage(email, body)
            }
        }
    }

    override fun showDataUser() {
        var name = myPref.getString("nama", "")
        var number = myPref.getString("nomor", "")
        var email = myPref.getString("email", "")
        var pass = myPref.getString("pass", "")

        edt_profile_nama.setText(name)
        edt_profile_number.setText(number)
        edt_profile_email.setText(email)
        edt_profile_pass.setText(pass)
    }

    override fun setFotoProfile() {
        var image = myPref.getString("image", "")
        if (image.equals("null")) {
            Toast.makeText(context, "Belum ada data gambar", Toast.LENGTH_SHORT).show()
        } else {
            Picasso.get().load(image).error(R.drawable.ic_launcher_background).into(view?.cmv_profile)
        }
    }

    override fun clearLoginData(): AlertDialog? {
        var builder = AlertDialog.Builder(context)
        builder.setTitle("Logout")
        builder.setMessage("Apakah anda ingin logout?")
        builder.setIcon(R.drawable.ic_warning)
        builder.setPositiveButton("Ya") { dialog, which ->
            editor.remove("id")
            editor.remove("nama")
            editor.remove("nomor")
            editor.remove("email")
            editor.remove("pass")
            editor.remove("image")
            loginEditor.remove("isLogin")
            editor.apply()
            loginEditor.apply()
            goToLogin()
        }
        builder.setNegativeButton("Tidak") { dialog, which ->  }
        builder.create()
        return builder.show()
    }

    override fun goToLogin() {
        val intent = Intent(this.activity, LoginActivity::class.java)
        startActivity(intent)
    }

    override fun uploadPhotoSucces(photo: String?) {
        Toast.makeText(context, "berhasil", Toast.LENGTH_SHORT).show()
    }

    override fun onFailure(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onSuccess(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            var extras: Bundle? = data?.extras
            var bitmap: Bitmap = extras?.get("data") as Bitmap

            var filesDir: File = context!!.filesDir
            var imageFile = File(filesDir, "image" + ".jpg")

            var os: OutputStream
            try {
                os = FileOutputStream(imageFile)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os)
                os.flush()
                os.close()
                file = imageFile
                view?.cmv_profile?.setImageBitmap(bitmap)
            } catch (e: Exception) {
                Log.e(javaClass.simpleName, "Error writing bitmap", e)
            }
        } else if (requestCode == REQUEST_CHOOSE_PHOTO && resultCode == RESULT_OK) {
            var uri: Uri? = data?.data

            activity?.let { CropImage.activity(uri).setAspectRatio(1, 1).start(it) }
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            var result: CropImage.ActivityResult = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK) {
                var imageUri: Uri = result.uri
                try {
                    var bitmap: Bitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver, imageUri)

                    var filesDir: File = context?.filesDir!!
                    var imageFile = File(filesDir, "image" + ".jpg")
                    editor.putString("image", imageFile.toString())

                    var os: OutputStream = FileOutputStream(imageFile)
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os)
                    os.flush()
                    os.close()
                    file = imageFile
                    if (bitmap != null) {
                        view?.cmv_profile?.setImageBitmap(bitmap)
                    } else {
                        setFotoProfile()
                    }
                } catch (e: IOException) {
                    Log.e(javaClass.simpleName, "Error writing bitmap", e)
                    e.printStackTrace()
                }
            }
        }
    }

    private fun cropImageAutoSelection() {
        activity?.let {
            CropImage.activity()
                .setAspectRatio(2, 2)
                .start(context!!, this)
        }
    }

    @AfterPermissionGranted(1)
    private fun checkCameraPermission() {
        var perm: String = Manifest.permission.CAMERA
        if (context?.let { EasyPermissions.hasPermissions(it, perm) }!!) {
        } else {
            EasyPermissions.requestPermissions(this, "Butuh permission camera", RC_CAMERA, perm)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

//    private fun getPicFromCamera() {
//        var takePictureIntent: Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        if (context?.packageManager?.let { takePictureIntent.resolveActivity(it) } != null) {
//            startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
//        }
//    }

}
