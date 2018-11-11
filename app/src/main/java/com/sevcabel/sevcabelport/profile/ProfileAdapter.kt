package com.sevcabel.sevcabelport.profile

import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sevcabel.sevcabelport.R
import com.sevcabel.sevcabelport.news.News
import com.sevcabel.sevcabelport.utils.SevcabelApplication
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.avatar_and_name_item.view.*
import kotlinx.android.synthetic.main.events_where_i_was_item.view.*
import android.R.string.cancel
import android.support.design.widget.Snackbar
import android.support.v4.view.accessibility.AccessibilityEventCompat.setAction
import android.app.Activity
import android.content.Intent
import android.view.MenuItem


class ProfileAdapter(val layoutInflater: LayoutInflater, private val items: MutableList<News>): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private var isFirstItem: Boolean = true

    class UserInfoHolder(view: View) : RecyclerView.ViewHolder(view){
        val avatarImage = view.avatar_image
        val nameText = view.name_text
    }

    class EventHolder(view: View) : RecyclerView.ViewHolder(view){
        val titleView = view.event_title
        val dateView = view.event_date
        val reviewButton = view.review_button
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) return  0
        else return 1
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(p0: ViewGroup, position: Int): RecyclerView.ViewHolder {
        when (getItemViewType(position)){
            0 -> {
                val view: View = layoutInflater.inflate(R.layout.avatar_and_name_item, p0, false)
                isFirstItem = false
                return UserInfoHolder(view)
            }
            else -> {
                val view: View = layoutInflater.inflate(R.layout.events_where_i_was_item, p0, false)
                return EventHolder(view)}
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType){
            0 -> {
                val userInfoHolder: UserInfoHolder = holder as UserInfoHolder
                userInfoHolder.nameText.text = "${SevcabelApplication.getSurname()} ${SevcabelApplication.getLastname()}"
                Picasso.get()
                        .load(SevcabelApplication.getAvatarLink())
                        .into(userInfoHolder.avatarImage)
            }

            else -> {
                val eventHolder: EventHolder = holder as EventHolder
                val new: News = SevcabelApplication.newsList[position]
                eventHolder.titleView.text = new.title
                eventHolder.dateView.text = new.date
                eventHolder.reviewButton.setOnClickListener({
                    val intent = Intent(SevcabelApplication.getContext(), CommentActivity::class.java)
                    SevcabelApplication.getContext().startActivity(intent)
                })
            }
        }
    }
}