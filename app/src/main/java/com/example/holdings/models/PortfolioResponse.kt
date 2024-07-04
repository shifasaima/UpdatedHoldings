package com.example.holdings.models

import com.google.gson.annotations.SerializedName

data class PortfolioResponse(
    @SerializedName("data") val data: UserData
)

data class UserData(
    @SerializedName("userHolding") val userHoldings: List<Holding>
)

data class Holding(
    @SerializedName("symbol") val symbol: String,
    @SerializedName("quantity") val quantity: Int,
    @SerializedName("ltp") val ltp: Double,
    @SerializedName("avgPrice") val avgPrice: Double,
    @SerializedName("close") val close: Double
)
