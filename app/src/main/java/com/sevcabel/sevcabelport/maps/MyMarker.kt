package com.sevcabel.sevcabelport.maps

data class MyMarker(
        val id: Int = 0,
        val title: String = "Title",
        val text: String = "Some text",
        val type: Int = 0,
        var markerPositionX: Double = 0.0,
        var markerPositionY: Double = 0.0)