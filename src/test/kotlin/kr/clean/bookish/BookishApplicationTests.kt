package kr.clean.bookish

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import kr.clean.bookish.model.BookDetail
import kr.clean.bookish.vc.home.BookMapper
import org.apache.http.HttpResponse
import org.apache.http.client.config.RequestConfig
import org.apache.http.client.methods.HttpGet
import org.apache.http.entity.ContentType
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils
import org.json.JSONObject
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.parser.Parser
import org.jsoup.select.Elements
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import java.io.BufferedReader
import java.io.InputStreamReader
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.stream.Collectors


@SpringBootTest
class BookishApplicationTests {

    val log: Logger = LoggerFactory.getLogger(this.javaClass)

    @Autowired
    lateinit var bookMapper: BookMapper

    @Test
    fun contextLoads() {
        val key = "9f318cfa6b9d91fc950d6c1feff5ac4219cb12bb9bf2a3b01f94432809134446"
        val today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
        val url = "https://nl.go.kr/NL/search/openApi/saseoApi.do?key=$key" +
                "&startRowNumApi=1&endRowNumApi=10&start_date=$today&nd_date=$today&drcode=11"
        log.debug("{}", url)

        val get = HttpGet(url)
        get.config = RequestConfig.custom()
            .setConnectTimeout(5000)
            .setSocketTimeout(5000)
            .setRedirectsEnabled(true)
            .build()

        val client : CloseableHttpClient = HttpClients.createDefault()

        val res: HttpResponse = client.execute(get)
        val contentType = ContentType.get(res.entity)
        val byteArray = EntityUtils.toByteArray(res.entity)
        EntityUtils.consumeQuietly(res.entity)

        val charset = contentType.charset
        val responseString = String(byteArray, charset)

        var parser: Parser? = null
        if (contentType.mimeType != null) {
            if (contentType.mimeType.lowercase().contains("xml")) {
                parser = Parser.xmlParser()
            } else {
                parser = Parser.htmlParser()
            }
        }

        var doc: Document
        try {
            doc = Jsoup.parse(responseString, url, parser)
        } catch(e: Exception) {
            log.error("URL 호출 및 파싱 오류", e)
            doc = Jsoup.parse("<html></html>")
        }

        val list: MutableList<BookDetail> = mutableListOf()
        for(isbn in doc.select("recomisbn")) {
            val book = BookDetail()
            book.isbn = isbn.text()
            list.add(book)
        }

        log.info("list >> $list")
    }

    @Test
    fun contextLoads2() {


        /*
       *url:'https://openapi.naver.com/v1/search/book.xml?query='+encodeURIComponent('타입스크립트')+'&display=10&start=1',
       type:'GET',
       headers: {'X-Naver-Client-Id':'SxxE9ZX8mhGHwE0khwl6','X-Naver-Client-Secret' :'XjkmirT8LN'}
       * */

        val clientId = "SxxE9ZX8mhGHwE0khwl6"
        val clientSecret = "XjkmirT8LN"
        val url = "https://openapi.naver.com/v1/search/book_adv.xml?d_isbn=9788961961844"
        log.debug("{}", url)

        val get = HttpGet(url)
        get.config = RequestConfig.custom()
            .setConnectTimeout(5000)
            .setSocketTimeout(5000)
            .setRedirectsEnabled(true)
            .build()
        get.addHeader("X-Naver-Client-Id", clientId)
        get.addHeader("X-Naver-Client-Secret", clientSecret)

        val client : CloseableHttpClient = HttpClients.createDefault()
        val res: HttpResponse = client.execute(get)
        val contentType = ContentType.get(res.entity)
        val byteArray = EntityUtils.toByteArray(res.entity)
        EntityUtils.consumeQuietly(res.entity)

        val charset = contentType.charset
        val responseString = String(byteArray, charset)

        log.info("byteArray >> $byteArray")

        var parser: Parser? = null
        if (contentType.mimeType != null) {
            if (contentType.mimeType.lowercase().contains("xml")) {
                parser = Parser.xmlParser()
            } else {
                parser = Parser.htmlParser()
            }
        }

        var doc: Document
        try {
            doc = Jsoup.parse(responseString, url, parser)
        } catch(e: Exception) {
            log.error("URL 호출 및 파싱 오류", e)
            doc = Jsoup.parse("<html></html>")
        }

        val el: Elements? = doc.select("item")
        var book = BookDetail()
        if (el != null) {
            val isbn = el.select("isbn").text().split(" ")[1]
            book = BookDetail(
                title = el.select("title").text()
                , description = el.select("description").text()
                , image = el.select("image").text()
                , author = el.select("author").text()
                , publisher = el.select("publisher").text()
                , isbn = isbn
                , pubDate = el.select("pubDate").text()
            )
        }

         bookMapper.insertBook(book)
    }

    @Test
    fun pathTest() {
        val pathResolver = PathMatchingResourcePatternResolver()
        log.info("path 2 >> ${pathResolver.getResources("classpath*:/**/*.xml").get(0)}")
        log.info("path 3 >> ${pathResolver.getResources("classpath*:/kotlin/**/*.xml").size}")
    }

//    @Test
//    fun contextLoads3() {
//        // http://seoji.nl.go.kr/landingPage/SearchApi.do?cert_key=[발급된키값]&result_style=json&page_no=1&page_size=10&start_publish_date=20170207&end_publish_date=20170207
//        val key = "9f318cfa6b9d91fc950d6c1feff5ac4219cb12bb9bf2a3b01f94432809134446"
//        val today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
//        val month = LocalDate.now().minusMonths(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"))
//        val url = "http://seoji.nl.go.kr/landingPage/SearchApi.do?cert_key=$key" +
//                "&result_style=json&page_no=1&page_size=10&start_publish_date=$month&end_publish_date=$today"
//        log.debug("{}", url)
//
//        val httpClient: CloseableHttpClient = HttpClients.createDefault()
//        val mapper = ObjectMapper()
//
//        val get = HttpGet(url)
//        val res = BufferedReader(
//            InputStreamReader(
//                httpClient.execute(get).entity.content
//            )
//        ).lines().collect(Collectors.joining())
//
//        val jsonObject = JSONObject(res)
//
//        val jsonList: List<Map<String, Any>> =
//            mapper.readValue(jsonObject.get("docs").toString(), object : TypeReference<List<Map<String, Any>>>() {})
//
//        val list = jsonList.stream()
//            .map { `val`: Map<String, Any> ->
//                `val`["EA_ISBN"]
//            }
//            .distinct()
//            .collect(Collectors.toList())
//
//        log.info("list >> $list")
//
//    }
}
