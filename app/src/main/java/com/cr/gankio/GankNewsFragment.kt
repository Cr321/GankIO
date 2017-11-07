package com.cr.gankio

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cr.gankio.data.GankRepository
import kotlinx.android.synthetic.main.fragment_news_list_layout.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * Created by RUI CAI on 2017/10/27.
 *
 */
class GankNewsFragment : Fragment() {
    companion object {
        fun newInstance() : GankNewsFragment {
            return GankNewsFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_news_list_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        getGanksNewsList()
    }

    fun getGanksNewsList() = doAsync {
        val news = GankRepository.getGanksNewsList().execute().body()!!.results
        uiThread {
            recyclerView.adapter = GankNewsAdapter(news) {
                val intent = Intent()
                intent.setClass(activity, WebActivity::class.java)
                intent.putExtra("url", it.url)
                startActivity(intent)
            }
        }
    }
}