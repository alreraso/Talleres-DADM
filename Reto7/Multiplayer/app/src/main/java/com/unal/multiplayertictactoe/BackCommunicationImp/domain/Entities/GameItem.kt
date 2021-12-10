package com.unal.multiplayertictactoe.BackCommunicationImp.domain.Entities

import java.io.Serializable

data class GameItem (
    val __v: Int,
    val _id: String,
    val matrix: String,
    val name: String,
    val player1: String,
    val player2: String,
    val status: String,
    val token: String,
    val winner: String
): Serializable
