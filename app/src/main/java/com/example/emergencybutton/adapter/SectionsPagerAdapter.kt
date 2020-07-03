package com.example.emergencybutton.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.emergencybutton.R
import com.example.emergencybutton.fragment.home.HomeFragment
import com.example.emergencybutton.fragment.notification.NotificationFragment
import com.example.emergencybutton.fragment.profile.ProfileFragment

class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    private val PAGE_TITLES = arrayOf(
        R.string.home,
        R.string.profile,
        R.string.notification
    )

    val page = listOf(
        HomeFragment(),
        ProfileFragment(),
        NotificationFragment()
    )

    override fun getItem(position: Int): Fragment {
        return page[position]
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(PAGE_TITLES[position])
    }
}