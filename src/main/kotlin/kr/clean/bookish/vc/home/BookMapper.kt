package kr.clean.bookish.vc.home

import kr.clean.bookish.annotation.LynnDB
import kr.clean.bookish.model.BookDetail
import org.apache.ibatis.annotations.Mapper

@LynnDB
@Mapper
interface BookMapper {

    fun insertBook(book: BookDetail): Int
    fun selectBook(): BookDetail
}