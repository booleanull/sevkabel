package com.sevcabel.sevcabelport.news

data class News(
        var date: String = "",
        var dateInt: String = "",
        var description: String = "",
        var fullDescription: String = "",
        var image: String = "",
        var isPaid: Boolean = false,
        var linkToTicket: String = "",
        var title: String = ""
)