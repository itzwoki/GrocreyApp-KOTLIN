package ghanam.com.abdo.dataclasses

import java.time.LocalDateTime

data class PreviousOrder(val date:LocalDateTime, val total: Float)
data class YourOrderModel(
    val orderId: String? = null,
    val userId: String? = null,
    val orderTotal: Double? = null,
    // Add more fields as needed
)
