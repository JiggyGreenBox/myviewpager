package com.example.myviewpager

data class TransactionSingle(
    val customerId: Int,
    val customerDisplayName: String,
    val litres: Double,
    val rate: Double,
    val amount: Double,
    val timestamp: String,
    val date: String,
    val car_no_plate: String,
    val trans_time: String,
    val t_string: String
)