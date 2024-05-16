package com.mahor.szlaki

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
class SectionsPagerAdapter(val context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return TrailListEasyFragment()
            1 -> return TrailListMediumFragment()
            2 -> return TrailListHardFragment()
        }
        return TrailListEasyFragment()
    }

    override fun getPageTitle(position: Int): CharSequence {
        when (position) {
            0 -> return context.getString(R.string.easy)
            1 -> return context.getString(R.string.medium)
            2 -> return context.getString(R.string.hard)
        }
        return context.getString(R.string.easy)
    }
}