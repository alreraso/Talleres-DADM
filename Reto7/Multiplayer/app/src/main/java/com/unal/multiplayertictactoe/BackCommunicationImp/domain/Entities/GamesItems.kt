package com.unal.multiplayertictactoe.BackCommunicationImp.domain.Entities

import java.io.Serializable

data class GamesItems (
    var name: String,
    var token: String
): Serializable