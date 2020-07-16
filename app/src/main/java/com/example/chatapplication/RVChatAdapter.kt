package com.example.chatapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlin.collections.ArrayList

class RVChatAdapter(context: Context)  : RecyclerView.Adapter<RVChatAdapter.ViewHolder>() {

    private var context: Context = context
    private var chatMessages: List<ChatModel> = ArrayList<ChatModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return if (viewType == 1) {
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.bubble_me,parent,false))
        } else {
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.bubble_their,parent,false))
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (chatMessages[position].sender == "user1") {
            1
        } else {
            2
        }
    }

    override fun getItemCount(): Int {
        return chatMessages.size
    }

    fun setData(chatData: ArrayList<ChatModel>) {
        this.chatMessages = chatData
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = chatMessages[position]

        holder.roomTextView.text = data.content
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val roomTextView: TextView = view.findViewById(R.id.tv_chat)

    }
}