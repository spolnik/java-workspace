package com.data

data class Customer(val id: Long, val firstName: String, val lastName: String)

fun main(args: Array<String>) {
    val customer = Customer(1L, "Mikolaj", "S")
    println(customer)
}