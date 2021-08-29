package kr.clean.bookish.model

data class BookDetails(
    var isbn: String? = null,
    val title: String? = null,
    val description: String? = null,
    val publisher: String? = null,
    val image: String? = null,
    val pubDate: String? = null
)