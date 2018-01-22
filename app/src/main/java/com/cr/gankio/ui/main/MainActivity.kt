package com.cr.gankio.ui.main

import android.os.Bundle
import com.cr.gankio.R
import com.cr.library.activity.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 *
 * @author RUI CAI
 * @date 2017/10/22
 */
class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setup()
    }

    private fun setup() {
        val adapter = MainTabAdapter(supportFragmentManager)

        viewpager.adapter = adapter

        tabs.setupWithViewPager(viewpager)
    }

}
