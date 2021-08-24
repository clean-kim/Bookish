package kr.clean.bookish

import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication(exclude = [DataSourceAutoConfiguration::class, MybatisAutoConfiguration::class])
@EnableAsync
@EnableCaching
class BookishApplication

fun main(args: Array<String>) {
    runApplication<BookishApplication>(*args)
}
