package com.example.td1.network

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Category (
    @SerializedName("name_fr")val name: String,
    @SerializedName("items") val items: List <Plate>
): Serializable