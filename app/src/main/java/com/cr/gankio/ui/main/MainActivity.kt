package com.cr.gankio.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.cr.gankio.R
import com.cr.gankio.ui.search.SearchActivity
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId) {
            R.id.action_search -> {
                startActivityWithoutExtras(SearchActivity::class.java)
            }
        }
        return true
    }
}
