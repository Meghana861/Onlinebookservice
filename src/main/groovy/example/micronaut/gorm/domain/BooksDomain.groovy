package example.micronaut.gorm.domain

import grails.gorm.annotation.Entity

import java.time.LocalDate

@Entity
class BooksDomain {
    String title
    String author
    int price
    LocalDate pubDate
    static constraints={
        title unique:true,nullable:false
    }
}
