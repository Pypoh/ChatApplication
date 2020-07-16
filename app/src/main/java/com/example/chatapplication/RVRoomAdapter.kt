package com.example.chatapplication

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import java.util.ArrayList

class RVRoomAdapter(context: Context) : RecyclerView.Adapter<RVRoomAdapter.ViewHolder>() {

    private var context: Context = context
    private var roomData: List<String> = ArrayList<String>()
    private var chatData: List<ChatModel> = ArrayList<ChatModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_room, parent, false)
        )
    }

    fun setData(roomData: List<String>) {
        this.roomData = roomData
    }

    override fun getItemCount(): Int {
        return roomData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val roomData = roomData[position]

        holder.roomTextView.text = roomData
        holder.roomTextView.setOnClickListener {
            intentToChat(roomData)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val roomTextView: TextView = view.findViewById(R.id.tv_roomID)
    }

    fun intentToChat(roomId: String) {
        val intent = Intent(context, ChatActivity::class.java)
        intent.putExtra("RoomId", roomId)
        context.startActivity(intent)
    }
}