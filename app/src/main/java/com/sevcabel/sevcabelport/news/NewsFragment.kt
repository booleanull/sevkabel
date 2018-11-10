package com.sevcabel.sevcabelport.news

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import android.widget.Toast
import com.sevcabel.sevcabelport.R
import com.sevcabel.sevcabelport.utils.SevcabelApplication
import com.sevcabel.sevcabelport.utils.showIf
import kotlinx.android.synthetic.main.fragment_news.*

class NewsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        calendarView.showIf(true,false)
        val recyclerViewAdapterNews = NewsRecyclerViewAdapter(SevcabelApplication.newsList)
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        recycler_view_news.layoutManager = layoutManager
        recycler_view_news.adapter = recyclerViewAdapterNews
    }
}
