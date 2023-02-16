package com.example.td1.network

import java.io.Serializable
import com.google.gson.annotations.SerializedName

class MenuResult(@SerializedName("data") val data: List<Category>): Serializable {

}