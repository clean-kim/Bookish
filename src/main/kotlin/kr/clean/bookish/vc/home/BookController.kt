package kr.clean.bookish.vc.home

import kr.clean.bookish.model.Book
import kr.clean.bookish.model.BookDetail
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@RequestMapping("/home")
class BookController {

    @Autowired
    lateinit var bookService: BookService

    @GetMapping("/recommend")
    @ResponseBody
    fun recommend(): List<BookDetail> {
        return bookService.recommend()
    }

    @GetMapping("/newest")
    @ResponseBody
    fun newest(): List<Book> {
        return bookService.newest()
    }

    @GetMapping("/search")
    @ResponseBody
    fun search(): List<BookDetail> {
        return bookService.search()
    }
}