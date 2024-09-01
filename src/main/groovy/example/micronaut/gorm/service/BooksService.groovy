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

        // Save the domain entity
        BooksDomain savedBook = booksDomain.save(flush: true, failOnError: true)

        // Convert back to BooksModel
        return new BooksModel(
                title: savedBook.title,
                author: savedBook.author,
                price: savedBook.price,
                pubDate: savedBook.pubDate
        )
    }

    @Transactional
    def getAllBooks() {
        List<BooksDomain> booksDomain = BooksDomain.findAll()
        return booksDomain
    }

    @Transactional
    def getBookById(Long id) {
        BooksDomain booksDomain = BooksDomain.findById(id)
        return booksDomain
    }

    @Transactional
    def updateById(Long id, BooksModel updateBookModel) {
        BooksDomain booksDomain = BooksDomain.findById(id)
        if (booksDomain) {
            booksDomain.title = updateBookModel.title
            booksDomain.author = updateBookModel.author
            booksDomain.price = updateBookModel.price
            booksDomain.pubDate = updateBookModel.pubDate
            booksDomain.save()
        }
        return updateBookModel

    }

    @Transactional
    def deleteById(Long id) {
        BooksDomain booksDomain = BooksDomain.findById(id)
        if (booksDomain) {
            booksDomain.delete()
            return "deleted successfully"
        }
    }
}


