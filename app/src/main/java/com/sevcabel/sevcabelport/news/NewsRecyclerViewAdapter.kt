package com.sevcabel.sevcabelport.news

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import java.util.ArrayList
import android.content.Intent
import android.net.Uri
import com.sevcabel.sevcabelport.R
import com.sevcabel.sevcabelport.R.id.calendar_view
import com.sevcabel.sevcabelport.utils.SevcabelApplication
import kotlinx.android.synthetic.main.fragment_news.*


class NewsRecyclerViewAdapter(private val Datalist: MutableList<News>) : RecyclerView.Adapter<NewsRecyclerViewAdapter.MyViewHolder>() {
    var listNews: MutableList<News> = Datalist
    val context: Context = SevcabelApplication.getContext()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recycler_view_item_news, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(listNews[position])
    }

    override fun getItemCount(): Int {
        return listNews.size
    }

    fun UpdateList(newData: ArrayList<News>) {
        listNews = newData
        notifyDataSetChanged()
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var image: ImageView
        internal var date: TextView
        internal var description: TextView

        init {
            image = itemView.findViewById(R.id.news_image)
            date = itemView.findViewById(R.id.news_date)
            description = itemView.findViewById(R.id.news_description)
        }

        fun bind(data: News) {
            Picasso.get().load(data.image).into(image)
            date.text = data.date
            description.text = data.description
        }
    }
}