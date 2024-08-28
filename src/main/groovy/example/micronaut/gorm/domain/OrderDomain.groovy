package example.micronaut.gorm.domain

import example.micronaut.gorm.model.OrderModel
import grails.gorm.annotation.Entity

import java.time.LocalDate

@Entity
class OrderDomain {
    static hasMany=[lineitems:LineItemsDomain]
    static belongsTo=[users:UserDomain] //Many Orders has one user (Many to One)
    LocalDate orderDate


}
