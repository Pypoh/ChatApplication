package com.example.chatapplication

import java.io.Serializable

data class ChatModel(
    val content: String,
    val sender: String
) : Serializable {
    constructor() : this(
        "",
        ""
    )
}