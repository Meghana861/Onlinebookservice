package example.micronaut.gorm.model

import java.time.LocalDate

class OrderModel {
    Long id
    Long userId
    LocalDate orderDate
    List<Long> bookId
}
