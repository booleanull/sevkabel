package com.sevcabel.sevcabelport.utils

import android.support.v4.content.ContextCompat

fun Int.getString() = SevcabelApplication.getContext().getString(this)

//fun Int.getDrawable() = SevcabelApplication.getContext().getDrawable(this)

fun Int.getColor() = ContextCompat.getColor(SevcabelApplication.getContext(), this)