package com.example.emergencybutton.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.emergencybutton.R
import com.example.emergencybutton.adapter.SectionsPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.text.FieldPosition

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sectionPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        vp_main.adapter = sectionPagerAdapter
        tab_detail.setupWithViewPager(vp_main)
    }
}
