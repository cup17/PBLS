package com.example.emergencybutton.activity.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.emergencybutton.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.notification_item.*
import kotlinx.android.synthetic.main.notification_item.tv_nama_barang
import retrofit2.Retrofit

class DetailActivity : AppCompatActivity(), DetailConstruct.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        showLoading()
        showData()
    }

    override fun showData() {
        if (intent.extras != null) {
            val bundle = intent.extras
            tv_nama_pemosting.text = bundle!!.getString("namaPemosting")
            tv_detail_nama_barang.text = bundle!!.getString("nama")
            tv_nomor_hp.text = bundle!!.getString("nomor")
            tv_lokasi_barang.text = bundle!!.getString("lokas")
            tv_tanggal_barang.text = bundle!!.getString("tanggal")
            tv_deskripsi_barang.text = bundle!!.getString("deskripsi")
            Glide.with(this).load(bundle!!.getString("image")).into(img_barang)
            Picasso.get().load(bundle!!.getString("image")).error(R.drawable.ic_error_outline_white_24dp).into(img_barang);
            hideLoading()
        }
    }

    override fun showLoading() {
        prb_loading!!.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        prb_loading!!.visibility = View.GONE
    }
}
