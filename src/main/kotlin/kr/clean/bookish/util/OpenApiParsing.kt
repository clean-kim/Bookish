package kr.clean.bookish.util

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import kr.clean.bookish.model.BookDetail
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
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.stream.Collectors
import kotlin.streams.toList

class OpenApiParsing {

    val log: Logger = LoggerFactory.getLogger(this.javaClass)

    fun nlXml(url: String): Document {
        log.debug("{}", url)
        log.info("{}", url)

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


    fun nlJson(url: String): List<String> {
        log.debug("{}", url)

        val httpClient: CloseableHttpClient = HttpClients.createDefault()
        val mapper = ObjectMapper()

        val get = HttpGet(url)
        val res = BufferedReader(
            InputStreamReader(
                httpClient.execute(get).entity.content
            )
        ).lines().collect(Collectors.joining())

        val jsonObject = JSONObject(res)

        val jsonList: List<Map<String, Any>> =
            mapper.readValue(jsonObject.get("docs").toString(), object : TypeReference<List<Map<String, Any>>>() {})

        val list = jsonList.stream()
            .map { `val`: Map<String, Any> ->
                `val`["EA_ISBN"].toString()
            }
            .toList()

        log.info("list >>> $list")
        return list
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

        var book = BookDetail()
        if (res.statusLine.statusCode == 200) {
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
            if (el != null && !el.isEmpty()) {
                log.info("el >> $el")
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
        }

        return book
    }
}