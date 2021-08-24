package kr.clean.bookish

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@RequestMapping("/test")
class TestController {

    val log: Logger = LoggerFactory.getLogger(this.javaClass)

    @RequestMapping("/hi")
    @ResponseBody
    fun hi(): String {
        log.info("hhhhiii?")
        return "hi"
    }

}