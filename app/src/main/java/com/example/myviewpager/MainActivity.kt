package com.example.myviewpager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.myviewpager.dummy.DummyContent
import com.google.android.material.tabs.TabLayout

class MainActivity : ItemFragment.OnListFragmentInteractionListener, AppCompatActivity() {

    override fun onListFragmentInteraction(item: DummyContent.DummyItem?) {
        Log.e("MainActivity", "interface implemented")
    }

    private lateinit var mPager: ViewPager
    private lateinit var tabs: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Instantiate a ViewPager and a PagerAdapter.
        mPager = findViewById(R.id.viewpager)
        val pagerAdapter = ScreenSlidePagerAdapter(supportFragmentManager)


        val firstFragment: MyFragment = MyFragment.newInstance("First Fragment")
        val secondFragment: MySecondFragment = MySecondFragment.newInstance("Second Fragment")
        val listFragment: ItemFragment = ItemFragment.newInstance(1)

        pagerAdapter.addFragment(listFragment, "ONE")
        pagerAdapter.addFragment(firstFragment, "TWO")
        pagerAdapter.addFragment(secondFragment, "THREE")

        // The pager adapter, which provides the pages to the view pager widget.

        mPager.adapter = pagerAdapter

        tabs = findViewById(R.id.tab_layout)
        tabs.setupWithViewPager(mPager)
    }


    class ScreenSlidePagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        private val mFragmentList: ArrayList<Fragment> = ArrayList()
        private val mFragmentTitleList: ArrayList<String> = ArrayList()

        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
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
