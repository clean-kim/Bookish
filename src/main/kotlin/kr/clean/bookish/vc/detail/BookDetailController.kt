package kr.clean.bookish.vc.detail

import kr.clean.bookish.model.BookDetail
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@RequestMapping("/detail")
class BookDetailController {

    @Autowired
    lateinit var bookDetailService: BookDetailService

    @RequestMapping("/info")
    @ResponseBody
    fun bookDetail(@RequestParam isbn: String): BookDetail {
        return bookDetailService.bookDetail(isbn)
    }


}