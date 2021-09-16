package kr.clean.bookish.vc.home

import kr.clean.bookish.model.BookDetail
import kr.clean.bookish.util.OpenApiParsing
import org.apache.http.HttpResponse
import org.apache.http.client.config.RequestConfig
import org.apache.http.client.methods.HttpGet
import org.apache.http.entity.ContentType
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.parser.Parser
import org.jsoup.select.Elements
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Service
class BookService {

    val log: Logger = LoggerFactory.getLogger(this.javaClass)

    @Autowired
    lateinit var bookMapper: BookMapper

    fun recommend(): MutableList<BookDetail> {
        val bookList: MutableList<BookDetail> = mutableListOf()
        nlRecommend().forEach { it.isbn?.let { it1 ->
            bookList.add(naverBookDetails(it1))
        } }

        return bookList
    }

    // 국립중앙도서관 - 사서추천도서
    fun nlRecommend(): MutableList<BookDetail> {
        val key = "9f318cfa6b9d91fc950d6c1feff5ac4219cb12bb9bf2a3b01f94432809134446"
        val today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
        val url = "https://nl.go.kr/NL/search/openApi/saseoApi.do?key=$key" +
                "&startRowNumApi=1&endRowNumApi=10&start_date=$today&nd_date=$today&drcode=11"

        val doc = OpenApiParsing().xml(key, url)

        val list: MutableList<BookDetail> = mutableListOf()
        for(isbn in doc.select("recomisbn")) {
            val book = BookDetail()
            book.isbn = isbn.text()
            list.add(book)
        }

        return list
    }

    // 네이버 - 책 상세 검색
    fun naverBookDetails(isbn: String): BookDetail {
        val clientId = "SxxE9ZX8mhGHwE0khwl6"
        val clientSecret = "XjkmirT8LN"
        val url = "https://openapi.naver.com/v1/search/book_adv.xml?d_isbn="+isbn
        log.debug("{}", url)

        val book = OpenApiParsing().naverXml(clientId, clientSecret, url)
        book.title = book.title?.split("(")?.get(0)?.trim()
        bookMapper.insertBook(book)

        return book
    }



}