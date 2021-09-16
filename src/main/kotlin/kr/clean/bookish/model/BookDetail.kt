package kr.clean.bookish.model

data class BookDetail(
    val no: Int? = 0,
    var isbn: String? = null,
    var title: String? = null,
    val description: String? = null,
    val author: String? = null,
    val publisher: String? = null,
    val image: String? = null,
    val pubDate: String? = null
)