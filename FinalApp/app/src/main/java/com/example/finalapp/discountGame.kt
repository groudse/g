package com.example.finalapp

class Games : ArrayList<GameBean>()

data class GameBean(
    val cheapest: String,
    val cheapestDealID: String,
    val external: String,
    val gameID: String,
    val internalName: String,
    val steamAppID: Any,
    val thumb: String
)

