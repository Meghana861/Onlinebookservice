package example.micronaut.gorm.controller

import example.micronaut.gorm.model.BooksModel
import example.micronaut.gorm.service.BooksService
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put
import org.hibernate.annotations.GeneratorType

import javax.inject.Inject
@Controller("/books")
class BooksController {
    @Inject
    BooksService booksService
    @Post("/save")
    def saveBooks(@Body BooksModel booksModel){
        booksService.saveBooks(booksModel)
    }
    @Get
    def getBooks(){
        booksService.getAllBooks()
    }
    @Get("/{id}")
    def getBookById(@PathVariable Long id){
        booksService.getBookById(id)
    }
    @Put("/{id}")
    def updateBookById(@PathVariable Long id,@Body BooksModel booksModel){
        booksService.updateById(id,booksModel)
    }
}
