package example.micronaut.gorm.domain

import grails.gorm.annotation.Entity

@Entity
class LineItemsDomain {
    static  belongsTo=[orders:OrderDomain,books:BooksDomain]
}
