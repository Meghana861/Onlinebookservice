package example.micronaut.gorm.service

import example.micronaut.gorm.domain.BooksDomain
import example.micronaut.gorm.model.BooksModel
import grails.gorm.transactions.Transactional

import javax.inject.Singleton

@Singleton
class BooksService {
    @Transactional
    def saveBooks(BooksModel booksModel) {
        // Convert BooksModel to BooksDomain
        BooksDomain booksDomain = new BooksDomain(
                title: booksModel.title,
                author: booksModel.author,
                price: booksModel.price,
                pubDate: booksModel.pubDate
        )
        BooksDomain savedBook=booksDomain.save(flush: true, failOnError: true)
        return convertModelEntity(savedBook)
    }

    @Transactional
    def getAllBooks() {
        List<BooksDomain> booksDomain = BooksDomain.findAll()
        return booksDomain.collect{it->convertModelEntity(it)}
    }

    @Transactional
    def getBookById(Long id) {
        BooksDomain booksDomain = BooksDomain.findById(id)
        return convertModelEntity(booksDomain)

    }

    @Transactional
    def updateById(Long id, BooksModel updateBookModel) {
        BooksDomain booksDomain = BooksDomain.findById(id)
        if (booksDomain) {
            booksDomain.title = updateBookModel.title
            booksDomain.author = updateBookModel.author
            booksDomain.price = updateBookModel.price
            booksDomain.pubDate = updateBookModel.pubDate
            BooksDomain updatedBook=booksDomain.save()
            return convertModelEntity(updateBook)
        }


    }

    @Transactional
    def deleteById(Long id) {
        BooksDomain booksDomain = BooksDomain.findById(id)
        if (booksDomain) {
            booksDomain.delete()
            return "deleted successfully"
        }
    }

    static BooksModel convertModelEntity(BooksDomain booksDomain) {
        return new BooksModel(
                title: booksDomain.title,
                author: booksDomain.author,
                price: booksDomain.price,
                pubDate: booksDomain.pubDate
        )
    }

}
