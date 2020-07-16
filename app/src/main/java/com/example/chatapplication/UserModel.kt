package com.example.chatapplication

import com.google.common.collect.Lists
import com.google.gson.annotations.SerializedName
import com.google.protobuf.LazyStringArrayList
import java.io.Serializable

data class UserModel(
    @SerializedName("email")
    val email: String,
    @SerializedName("nama")
    val nama: String,
    @SerializedName("chatrooms")
    val chatrooms: ArrayList<String>
) : Serializable {
    constructor() : this(
        "",
        "",
        arrayListOf<String>()
    )
}