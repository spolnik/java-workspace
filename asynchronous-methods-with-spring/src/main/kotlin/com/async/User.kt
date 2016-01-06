package com.async

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class User(var name: String, var blog: String) {
    constructor() : this("", "")
}