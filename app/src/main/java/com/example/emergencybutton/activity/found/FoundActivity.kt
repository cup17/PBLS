package com.example.emergencybutton.activity.found

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.InputType
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.emergencybutton.R
import com.example.emergencybutton.activity.MainActivity
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.activity_found.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*

class FoundActivity : AppCompatActivity(), FoundConstruct.View {

    private val presenter: FoundPresenter = FoundPresenter(this)
    lateinit var myPref: SharedPreferences

    private val RC_CAMERA = 1
    val REQUEST_TAKE_PHOTO = 1
    val REQUEST_CHOOSE_PHOTO = 2

    lateinit var file: File

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_found)

        edt_found_tanggal.inputType = InputType.TYPE_NULL

        myPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE)

        imb_found_barang.setOnClickListener { goToHome() }

        tv_found_nama_pengguna.text = myPref.getString("nama", "").toString()

        edt_found_tanggal.setOnClickListener {
            val newCalendar = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(
                this@FoundActivity,
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    val newDate = Calendar.getInstance()
                    newDate[year, month] = dayOfMonth
                    val dateFormatter =
                        SimpleDateFormat("yyyy-MM-dd", Locale.US)
                    edt_found_tanggal.setText(dateFormatter.format(newDate.time).toString())
                },
                newCalendar[Calendar.YEAR],
                newCalendar[Calendar.MONTH],
                newCalendar[Calendar.DAY_OF_MONTH]
            )
            datePickerDialog.show()
        }

        imb_found_barang.setOnClickListener {
            checkCameraPermission()
            cropImageAutoSelection()
        }

        btn_upload_found.setOnClickListener {

            var requestFile : RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
            var roleType : RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), "4")
            var nama : RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), myPref.getString("nama", "").toString())
            var namaBarang : RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), edt_found_nama_barang.text.toString())
            var nomorHP : RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), edt_found_nomor_hp.text.toString())
            var tanggal : RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), edt_found_tanggal.text.toString())
            var lokasi : RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), edt_found_lokasi.text.toString())
            var deskripsi : RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), edt_found_deskripsi.text.toString())
            var body : MultipartBody.Part = MultipartBody.Part.createFormData("image", file.path, requestFile)

//            if (namaBarang.isEmpty() || nomorHP.isEmpty() || tanggal.isEmpty() || lokasi.isEmpty() || deskripsi.isEmpty()) {
//                if (namaBarang.isEmpty()) {
//                    edt_nama_barang.error = "Kolom tidak boleh kosong!"
//                }
//
//                if (nomorHP.isEmpty()) {
//                    edt_nomor_hp.error = "Kolom tidak boleh kosong!"
//                }
//
//                if (tanggal.isEmpty()) {
//                    edt_tanggal.error = "Kolom tidak boleh kosong!"
//                }
//
//                if (lokasi.isEmpty()) {
//                    edt_lokasi.error = "Kolom tidak boleh kosong!"
//                }
//
//                if (deskripsi.isEmpty()) {
//                    edt_deskripsi.error = "Kolom tidak boleh kosong!"
//                }
//            } else {
            if (cbx_found_agree.isChecked) {
                var fileGambar: File? = file
                if (fileGambar != null) {
                    presenter.pushDataFound(
                        roleType,
                        namaBarang,
                        nama,
                        nomorHP,
                        tanggal,
                        lokasi,
                        deskripsi,
                        body)
                    goToHome()
                } else {
                    Toast.makeText(this, "Gambarnya masih kosong", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(
                    this,
                    "Anda harus menyetujui syarat dan ketentuan",
                    Toast.LENGTH_SHORT
                ).show()
            }
//            }
        }
    }

    override fun onSuccess(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        Log.d("berhasil", msg)
    }

    override fun onFailure(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        Log.d("gagal", msg)
    }

    override fun goToHome() {
        val intent = Intent(this@FoundActivity, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {
            var extras: Bundle? = data?.extras
            var bitmap: Bitmap = extras?.get("data") as Bitmap

            var filesDir: File = applicationContext.filesDir
            var imageFile = File(filesDir, "image" + ".jpg")

            var os: OutputStream
            try {
                os = FileOutputStream(imageFile)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os)
                os.flush()
                os.close()
                file = imageFile
                img_found_barang?.setImageBitmap(bitmap)
            } catch (e: Exception) {
                Log.e(javaClass.simpleName, "Error writing bitmap", e)
            }
        } else if (requestCode == REQUEST_CHOOSE_PHOTO && resultCode == Activity.RESULT_OK) {
            var uri: Uri? = data?.data

            CropImage.activity(uri).setAspectRatio(1, 1).start(this)
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            var result: CropImage.ActivityResult = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                var imageUri: Uri = result.uri
                try {
                    var bitmap: Bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)

                    var filesDir: File = this.filesDir!!
                    var imageFile = File(filesDir, "image" + ".jpg")

                    var os: OutputStream = FileOutputStream(imageFile)
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os)
                    os.flush()
                    os.close()
                    file = imageFile
                    if (bitmap != null) {
                        img_found_barang?.setImageBitmap(bitmap)
                    }
                } catch (e: IOException) {
                    Log.e(javaClass.simpleName, "Error writing bitmap", e)
                    e.printStackTrace()
                }
            }
        }
    }

    private fun cropImageAutoSelection() {
        this.let {
            CropImage.activity()
                .setAspectRatio(2, 2)
                .start(this)
        }
    }

    @AfterPermissionGranted(1)
    private fun checkCameraPermission() {
        var perm: String = Manifest.permission.CAMERA
        if (this.let { EasyPermissions.hasPermissions(it, perm) }!!) {
        } else {
            EasyPermissions.requestPermissions(this, "Butuh permission camera", RC_CAMERA, perm)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }
}
