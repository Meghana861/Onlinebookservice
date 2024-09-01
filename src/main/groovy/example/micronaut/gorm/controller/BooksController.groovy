package example.micronaut.gorm.controller

import example.micronaut.gorm.model.BooksModel
import example.micronaut.gorm.service.BooksService
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put
import io.micronaut.http.annotation.Status
import org.hibernate.annotations.GeneratorType

import javax.inject.Inject
@Controller("/books-process")
class BooksController {
    @Inject
    BooksService booksService
    @Post
    @Status(HttpStatus.CREATED)
    HttpResponse<BooksModel> saveBooks(@Body BooksModel booksModel){
        BooksModel book=booksService.saveBooks(booksModel)
        if(book){
            return HttpResponse.created(book)
        }
        else{
            return HttpResponse.badRequest("Failed to Add Book")
        }
    }
    @Get
    def getBooks() {
        def books = booksService.getAllBooks()
        if (books) {
            return HttpResponse.ok(books)
        } else {
            return HttpResponse.notFound("No books found")
        }
    }

    @Get("/{id}")
    def getBookById(@PathVariable Long id) {
        def book = booksService.getBookById(id)
        if (book) {
            return HttpResponse.ok(book)
        } else {
            return HttpResponse.notFound("Book with ID ${id} not found")
        }
    }

    @Put("/{id}")
    def updateBookById(@PathVariable Long id, @Body BooksModel booksModel) {
        def updatedBook = booksService.updateById(id, booksModel)
        if (updatedBook) {
            return HttpResponse.ok(updatedBook)
        } else {
            return HttpResponse.notFound("Failed to update book with ID ${id}")
        }
    }

    @Delete("/{id}")
    def deleteBookById(@PathVariable Long id) {
        def isDeleted = booksService.deleteById(id)
        if (isDeleted) {
            return HttpResponse.noContent()
        } else {
            return HttpResponse.notFound("Failed to delete book with ID ${id}")
        }
    }
}