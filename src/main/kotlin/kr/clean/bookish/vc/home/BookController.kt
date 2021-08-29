package kr.clean.bookish.vc.home

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/home")
class BookController {

    @Autowired
    lateinit var bookService: BookService

    fun recommend() {

    }
}