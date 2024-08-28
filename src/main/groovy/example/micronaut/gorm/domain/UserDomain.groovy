package example.micronaut.gorm.domain

import grails.gorm.annotation.Entity

@Entity
class UserDomain {
    String name
    String email
    String password
    String phoneNumber
    String address //domain
//    static constraints={
//        name  unique: true,nullable: false
//        address blank: false, nullable: false
//    }
}
