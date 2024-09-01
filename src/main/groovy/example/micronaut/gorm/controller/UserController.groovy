package example.micronaut.gorm.controller

import example.micronaut.gorm.domain.UserDomain
import example.micronaut.gorm.model.UserModel
import example.micronaut.gorm.service.UserService
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put
import io.micronaut.http.annotation.QueryValue
import io.micronaut.http.annotation.Status

import javax.inject.Inject
import java.nio.file.Path

@Controller("/users-process")
class UserController {
    @Inject
    UserService userService

    @Post
    @Status(HttpStatus.CREATED)
    def saveUsers(@Body UserModel userModel){
        try {
           def user= userService.saveUser(userModel)
            print user
            if(user){
                return HttpResponse.created(user)
            }
            else{
                return HttpResponse.badRequest("Failed to add user")
            }
        }
        catch (Exception e) {
            return HttpResponse.serverError("An error occurred: ${e.message}")
        }
    }

    @Get
    @Status(HttpStatus.CREATED)
    def getUsers(){
      def userModelList= userService.getAllUsers()
        if(userModelList){
            return HttpResponse.ok(userModelList)
        }
        else{
            return HttpResponse.notFound("Users Not Found")
        }
    }

    @Post("/login")
    def getLogin(@Body UserModel userModel){
        return userService.getUserByEmailAndPassword(userModel.email,userModel.password)
    }

    @Put("/{id}")
    @Status(HttpStatus.CREATED)
    def updateUser(@PathVariable Long id,@Body UserModel userModel){
        def updatedUser=userService.updateUser(id,userModel)
        if(updatedUser){
            return HttpResponse.ok(updatedUser)
        }
        else{
            return HttpResponse.notFound("User with ${id} Not Found to Update ")
        }
    }

    @Get("/{id}")
    def getById(@PathVariable Long id){
        return userService.getUserById(id)
    }

    @Delete("/{id}")
    @Status(HttpStatus.CREATED)
    def deleteById(@PathVariable Long id){
        String deleteUser= userService.deleteById(id)
        if(deleteUser){
            return HttpResponse.noContent()
        }
        else{
            return HttpResponse.notFound("User Not Found with id ${id}")
        }
    }

    @Get("/getnames")
    def getNames(){
        return userService.getByNames()
    }
}
