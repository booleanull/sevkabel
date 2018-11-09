package com.sevcabel.sevcabelport

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.sevcabel.sevcabelport.adapters.BottomBarAdapter
import com.sevcabel.sevcabelport.maps.MapsFragment
import com.sevcabel.sevcabelport.news.NewsFragment
import com.sevcabel.sevcabelport.profile.ProfileFragment
import com.sevcabel.sevcabelport.utils.getColor
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_news -> {
                viewPager.currentItem = 0
                title = getText(R.string.title_news)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_map -> {
                viewPager.currentItem = 1
                title = getText(R.string.title_map)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_profile -> {
                viewPager.currentItem = 2
                title = getText(R.string.title_profile)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setLogo(R.drawable.logo_white)
        title = getText(R.string.title_news)
        toolbar.setTitleTextColor(R.color.primaryTextColor.getColor())
        toolbar.setTitleMargin(70, 1, 1, 1)
        viewPager.setPagingEnabled(false)
        val pagerAdapter = BottomBarAdapter(supportFragmentManager)
        val fragmentMap = MapsFragment()
        val fragmentNews = NewsFragment()
        val fragmentProfile = ProfileFragment()
        pagerAdapter.addFragments(fragmentNews)
        pagerAdapter.addFragments(fragmentMap)
        pagerAdapter.addFragments(fragmentProfile)
        viewPager.adapter = pagerAdapter
        viewPager.currentItem = 0
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
}
