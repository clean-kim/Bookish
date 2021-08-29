package kr.clean.bookish

import kr.clean.bookish.model.BookDetails
import org.apache.http.HttpResponse
import org.apache.http.client.config.RequestConfig
import org.apache.http.client.methods.HttpGet
import org.apache.http.entity.ContentType
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.parser.Parser
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@SpringBootTest
class BookishApplicationTests {

    val log: Logger = LoggerFactory.getLogger(this.javaClass)

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

        val list: MutableList<BookDetails> = mutableListOf()
        for(isbn in doc.select("recomisbn")) {
            val book = BookDetails()
            book.isbn = isbn.text()
            list.add(book)
        }

        log.info("list >> $list")
    }

}
