package example.micronaut.gorm.service

import example.micronaut.gorm.domain.BooksDomain
import example.micronaut.gorm.domain.LineItemsDomain
import example.micronaut.gorm.domain.OrderDomain
import example.micronaut.gorm.domain.UserDomain
import example.micronaut.gorm.model.OrderModel
import grails.gorm.transactions.Transactional

import javax.inject.Singleton

@Singleton
class OrderService {
    /*Save Orders*/
    @Transactional
    def saveOrders(OrderModel orderModel){
        OrderDomain orderDomain=new OrderDomain()
        orderDomain.orderDate=orderModel.orderDate
        UserDomain userDomain=UserDomain.findById(orderModel.userId)
        if(userDomain) {
            orderDomain.users =userDomain
            orderDomain.save()
        }

        orderModel.bookId.each{
            BooksDomain booksDomain=BooksDomain.findById(it)
            if(booksDomain){
                LineItemsDomain lineItemsDomain=new LineItemsDomain()
                lineItemsDomain.orders=orderDomain
                lineItemsDomain.books=booksDomain
                lineItemsDomain.save()
            }
        }
        return "you order id is${orderDomain.id}"
    }
    /*Get Order By orderId*/
   @Transactional
   def getOrderById(Long id){
       OrderDomain orderDomain=OrderDomain.findById(id)
       if(orderDomain) {
           OrderModel orderModel = new OrderModel()
           orderModel.orderDate = orderDomain.orderDate
           orderModel.userId =orderDomain.users.id
           orderModel.bookId=orderDomain.lineitems.collect{it.books.id}
           return orderModel
       }
   }

    /*Get All Orders*/
    @Transactional
    List<OrderModel> getAllOrders(){
        List<OrderDomain> orderDomains=OrderDomain.list()
        List<OrderModel> orderModels=[]
         orderDomains.each{orderDomain->
            OrderModel orderModel=new OrderModel()
            orderModel.orderDate=orderDomain.orderDate
            orderModel.bookId=orderDomain.lineitems.collect{it.books.id}
             orderModels << orderModel
        }
        return orderModels
    }

    /*Get order by userId*/
    @Transactional
    def getOrderByUserId(Long userId){
        def user=UserDomain.findById(userId)
        if(user){
            OrderDomain orderDomain=OrderDomain.findByUsers(user)
            if(orderDomain) {
                OrderModel orderModel = new OrderModel()
                id:orderModel.id
                orderModel.orderDate = orderDomain.orderDate
                orderModel.bookId = orderDomain.lineitems.collect{ it.books.id }
                return orderModel
            }
        }

    }

    /*Delete By Id*/
    @Transactional
    def deleteById(Long id){
        OrderDomain orderDomain=OrderDomain.findById(id)
        if(orderDomain){
            orderDomain.delete()
            return "deleted successfully"
        }
    }

    /*Update By Id*/
    @Transactional
    def updateById(Long id, OrderModel updateOrderModel) {
        OrderDomain orderDomain = OrderDomain.findById(id)
        if (orderDomain) {
            orderDomain.lineitems.each{lineitem->lineitem.delete()}
            orderDomain.lineitems.clear()
            orderDomain.orderDate = updateOrderModel.orderDate
            updateOrderModel.bookId.each {
                BooksDomain booksDomain = BooksDomain.findById(it)
                if (booksDomain) {
                    LineItemsDomain lineItemsDomain = new LineItemsDomain()
                    lineItemsDomain.books = booksDomain
                    lineItemsDomain.orders = orderDomain
                    orderDomain.addToLineitems(lineItemsDomain)
                }
            }
            orderDomain.save(flush: true)
            return "updated successfully"
        }
    }


}