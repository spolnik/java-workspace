package com.data

data class Book(val isbn: String, val title: String)

fun main(args: Array<String>) {
    val book = Book("ASD-1234", "ABC for Dummies")
    println(book)
}