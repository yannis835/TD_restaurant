package com.example.td1.network

import java.io.Serializable

data class Item(
    var id: Int,
    var name: String,
    var quantity: Int,
    var image: String,
    var price: Float
) : Serializable