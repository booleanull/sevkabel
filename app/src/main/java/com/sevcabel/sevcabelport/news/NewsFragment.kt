package com.sevcabel.sevcabelport.news

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.*
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
        setHasOptionsMenu(true)
        calendar_view.showIf(true, false)
        val recyclerViewAdapterNews = NewsRecyclerViewAdapter(SevcabelApplication.newsList)
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recycler_view_news.layoutManager = layoutManager
        recycler_view_news.adapter = recyclerViewAdapterNews
        calendar_view.setOnDateChangeListener { _, year, month, dayOfMonth ->
            for (i in 0 until SevcabelApplication.newsList.size) {
                if (SevcabelApplication.newsList[i].dateInt == (dayOfMonth.toString() + '.' + month.toString() + '.' + year.toString())) {
                    recycler_view_news.smoothScrollToPosition(i)
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.calendar_see -> {
                if (calendar_view.visibility == View.GONE) {
                    calendar_view.showIf(false)
                } else {
                    calendar_view.showIf(true, false)
                }
                return true
            }
            R.id.calendar_search -> {
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
