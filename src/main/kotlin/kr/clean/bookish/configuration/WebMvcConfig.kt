package kr.clean.bookish.configuration

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfig : WebMvcConfigurer {
    val log: Logger = LoggerFactory.getLogger(this.javaClass)

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/appjs/**/*.js")
            .addResourceLocations("classpath:/")
    }
}