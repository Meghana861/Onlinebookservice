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
    @Transactional
    def saveOrders(OrderModel orderModel) {
        OrderDomain orderDomain = new OrderDomain()
        orderDomain.orderDate = orderModel.orderDate
        UserDomain user = UserDomain.findById(orderModel.userId)
        if (user) {
            orderDomain.users = user
            orderDomain.save()
        }
        orderModel.bookId.each {
            BooksDomain book = BooksDomain.findById(it)
            if (book) {
                LineItemsDomain lineItems = new LineItemsDomain()
                lineItems.orders = orderDomain
                lineItems.books = book
                lineItems.save()
            }
        }
        return "your order id is:${orderDomain.id}"
    }

    /*Get Order By orderId*/

    @Transactional
    def getOrderById(Long id) {
        OrderModel orderModel = new OrderModel()
        OrderDomain orderDomain = OrderDomain.findById(id)
        if (orderDomain) {
            orderModel.orderDate = orderDomain.orderDate
            orderModel.userId = orderDomain.users.id
            orderModel.bookId = orderDomain.lineitems.collect { it.books.id }
            /* 1. here we have to use orderDomain.lineitems instead of lineItemsDomain because lineItemsDomain represents a single instance but the
              orderDomain.lineitems represents the collection because lineitems as a whole collection saved as a property in the class orderDomain
               2. orderDomain.lineitems is a collection of LineItemsDomain
               3. .collect { it.books.id } iterates over each LineItemsDomain object in the lineitems collection.*/
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