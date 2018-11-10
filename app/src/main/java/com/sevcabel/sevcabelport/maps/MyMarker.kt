package com.sevcabel.sevcabelport.maps

import com.google.android.gms.maps.model.Marker

data class MyMarker (
        val id: Int = 0,
        val title: String = "Title",
        val text: String = "Some text",
        val type: Int = 0,
        val marker: Marker? = null)