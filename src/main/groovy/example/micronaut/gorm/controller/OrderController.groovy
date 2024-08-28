package example.micronaut.gorm.controller

import example.micronaut.gorm.model.OrderModel
import example.micronaut.gorm.service.OrderService
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put

import javax.inject.Inject

@Controller("/orders")
class OrderController {
    @Inject
    OrderService orderService
    @Post
    def saveOrders(@Body OrderModel orderModel){
        return orderService.saveOrders(orderModel)
    }
    @Get("/{id}")
    def getOrderById(@PathVariable Long id){
        return orderService.getOrderById(id)
    }
    @Get
    def getAllOrders(){
        return orderService.getAllOrders()
    }
    @Get("/userId/{userId}")
   def getOrderByUserId(@PathVariable Long userId){
        return orderService.getOrderByUserId(userId)
    }
    @Delete("/{id}")
    def deleteOrder(@PathVariable Long id){
        return orderService.deleteById(id)
    }
    @Put("/{id}")
    def updateOrder(@PathVariable Long id,@Body OrderModel updateOrderModel){
        return orderService.updateById(id,updateOrderModel)
    }
}
