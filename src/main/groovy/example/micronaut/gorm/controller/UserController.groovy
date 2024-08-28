package example.micronaut.gorm.controller

import example.micronaut.gorm.domain.UserDomain
import example.micronaut.gorm.model.UserModel
import example.micronaut.gorm.service.UserService
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put
import io.micronaut.http.annotation.QueryValue

import javax.inject.Inject
import java.nio.file.Path

@Controller("/users")
class UserController {
    @Inject
    UserService userService

    @Post("/save")
    def saveUsers(@Body UserModel userModel){
        userService.saveUser(userModel)
    }

    @Get
    def getUsers(){
      userService.getAllUsers()
    }

    @Post("/login")
    def getLogin(@Body UserModel userModel){
        return userService.getUserByEmailAndPassword(userModel.email,userModel.password)
    }

    @Put("/update/{id}")
    def updateUser(@PathVariable Long id,@Body UserModel userModel){
        return userService.updateUser(id,userModel)
    }

    @Get("/{id}")
    def getById(@PathVariable Long id){
        return userService.getUserById(id)
    }

    @Delete("/{id}")
    def deleteById(@PathVariable Long id){
        return userService.deleteById(id)
    }

    @Get("/getnames")
    def getNames(){
        return userService.getByNames()
    }
}
