package kr.clean.bookish.util

import kr.clean.bookish.model.BookDetail
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

class OpenApiParsing {

    val log: Logger = LoggerFactory.getLogger(this.javaClass)

    fun xml(key: String, url: String): Document {
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

        return doc
    }

    fun naverXml(clientId: String, clientSecret: String, url: String): BookDetail {
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
            val image = el.select("image").text().replace("type=m1&", "")
            book = BookDetail(
                title = el.select("title").text()
                , description = el.select("description").text()
                , image = image
                , author = el.select("author").text()
                , publisher = el.select("publisher").text()
                , isbn = isbn
                , pubDate = el.select("pubDate").text()
            )
        }

        return book
    }
}