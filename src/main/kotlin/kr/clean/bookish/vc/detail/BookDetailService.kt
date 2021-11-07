package kr.clean.bookish.vc.detail

import kr.clean.bookish.model.BookDetail
import kr.clean.bookish.vc.home.BookMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class BookDetailService {

    @Autowired
    lateinit var bookMapper: BookMapper

    fun bookDetail(isbn: String): BookDetail {
        return bookMapper.selectBook(isbn)
    }
}