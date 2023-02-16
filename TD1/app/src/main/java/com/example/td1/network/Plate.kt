package com.example.td1.network

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Plate(
        @SerializedName("name_fr") val name: String,
        @SerializedName("images") val images: Array<String>,
        @SerializedName("prices") val prices: List<Price>,
        @SerializedName("ingredients") val ingredients: Array<Ingredient>,
        @SerializedName("id") val id: Int
): Serializable