package com.example.myviewpager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    private lateinit var mPager: ViewPager
    private lateinit var tabs: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // old code
//        val manager = supportFragmentManager
//        val transaction = manager.beginTransaction()
//        transaction.add(R.id.main_frame_layout, MyFragment())
//        transaction.commit()


        // Instantiate a ViewPager and a PagerAdapter.
        mPager = findViewById(R.id.viewpager)
        val pagerAdapter = ScreenSlidePagerAdapter(supportFragmentManager)


        val firstFragment: MyFragment = MyFragment.newInstance("First Fragment")
        val secondFragment: MySecondFragment = MySecondFragment.newInstance("Second Fragment")

        pagerAdapter.addFragment(firstFragment, "ONE")
        pagerAdapter.addFragment(secondFragment, "TWO")

        // The pager adapter, which provides the pages to the view pager widget.

        mPager.adapter = pagerAdapter

        tabs = findViewById(R.id.tab_layout)
        tabs.setupWithViewPager(mPager)
    }


    class ScreenSlidePagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        private val mFragmentList: ArrayList<Fragment> = ArrayList()
        private val mFragmentTitleList: ArrayList<String> = ArrayList()

        override fun getItem(position: Int): Fragment {
            return mFragmentList.get(position)
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }


        fun addFragment(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mFragmentTitleList[position]
        }
    }
}
