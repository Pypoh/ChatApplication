package com.example.chatapplication

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class ChatActivity : AppCompatActivity() {

    // View
    private lateinit var chatRV: RecyclerView
    private lateinit var btnRefresh: Button

    private lateinit var chatAdapter: RVChatAdapter

    private var chatroomsModels: ArrayList<ChatModel> = arrayListOf<ChatModel>()

    private val mRef: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        setupViewBinding()

        getIntentExtra()

    }

    private fun getDataChat(roomId: String) {
        // Get Messages Data
//        mRef.collection("chatrooms").document(roomId).collection("messages").get()
//            .addOnCompleteListener {
//                for (item in it.result!!.documents) {
//                    val chatData = item.toObject(ChatModel::class.java)
//
//                    // Add chat models to ArrayList
//                    chatroomsModels.add(chatData!!)
//                    Log.d("MainActivity", "TestDataDebug Chat: " + chatData!!.content)
//                }
//            }

        setRoomRV()
        setListener(roomId)
    }

    private fun setListener(roomId: String) {
        // Get Messages Data
        mRef.collection("chatrooms").document(roomId).collection("messages")
            .addSnapshotListener { snapshot, e ->
                for (dc in snapshot!!.documentChanges) {
                    when (dc.type) {
                        DocumentChange.Type.ADDED -> {
                            val newDoc = dc.document.toObject(ChatModel::class.java)

                            chatroomsModels.add(newDoc)

                            chatAdapter.notifyDataSetChanged()
                        }

                        DocumentChange.Type.MODIFIED -> {

                        }

                        DocumentChange.Type.REMOVED -> {

                        }
                    }


                }
            }
    }

    private fun getIntentExtra() {

        val roomId =
            intent.extras!!.getString("RoomId")

        if (roomId != null) {
            getDataChat(roomId)
        }
    }

    private fun setRoomRV() {
        chatRV.layoutManager = LinearLayoutManager(this)
        chatAdapter = RVChatAdapter(this)
        chatAdapter.setData(chatroomsModels)
        chatRV.adapter = chatAdapter

        chatAdapter.notifyDataSetChanged()
    }

    private fun setupViewBinding() {
        chatRV = findViewById(R.id.rv_chat)
    }
}