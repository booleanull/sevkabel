package com.sevcabel.sevcabelport.utils

import android.support.v4.content.ContextCompat
import android.view.View

fun Int.getString() = SevcabelApplication.getContext().getString(this)

fun Int.getDrawable() = SevcabelApplication.getContext().getDrawable(this)

fun Int.getColor() = ContextCompat.getColor(SevcabelApplication.getContext(), this)

fun View.showIf(condition: Boolean, invisible: Boolean = false) {
    if(condition && invisible) {
        this.visibility = View.INVISIBLE
    }
    else if(condition) {
        this.visibility = View.GONE
    }
    else {
        this.visibility = View.VISIBLE
    }
}