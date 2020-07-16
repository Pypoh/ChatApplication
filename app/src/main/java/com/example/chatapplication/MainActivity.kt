package com.example.chatapplication

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.solver.widgets.ResolutionNode.REMOVED
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore


class MainActivity : AppCompatActivity() {

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    private val mRef: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    // View
    private lateinit var roomRV: RecyclerView
    private lateinit var btnRefresh: Button

    private lateinit var roomAdapter: RVRoomAdapter

    private val roomIds: ArrayList<String> = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loginHardCode()

        setupViewBinding()

        getDataFromFirestore()


    }

    private fun setupViewBinding() {
        roomRV = findViewById(R.id.rv_room)
        btnRefresh = findViewById(R.id.btn_refresh)

        btnRefresh.setOnClickListener {
            roomAdapter.notifyDataSetChanged()
        }


    }

    private fun setRoomRV(roomsData: ArrayList<String>, chatData: ArrayList<ChatModel>) {
        roomRV.layoutManager = LinearLayoutManager(this)
        roomAdapter = RVRoomAdapter(this)
        roomAdapter.setData(roomsData)
        roomRV.adapter = roomAdapter

        roomAdapter.notifyDataSetChanged()
    }

    private fun getDataFromFirestore() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid

        val chatroomsModels = arrayListOf<ChatModel>()


        val docRef = mRef.collection("users").document(userId.toString())
        docRef.get().addOnSuccessListener { documentSnapshot ->
            val userData = documentSnapshot.toObject(UserModel::class.java)
            Log.d("MainActivity", "TestDataDebug Nama: " + userData!!.nama)
            for (id in userData.chatrooms) {

                // Add Ids to ArrayList
                roomIds.add(id)

                Log.d("MainActivity", "RoomDataInner: " + roomIds[0])


//                // Get Messages Data
//                mRef.collection("chatrooms").document(id).collection("messages").get()
//                    .addOnCompleteListener {
//                        for (item in it.result!!.documents) {
//                            val chatData = item.toObject(ChatModel::class.java)
//
//                            // Add chat models to ArrayList
//                            chatroomsModels.add(chatData!!)
//                            Log.d("MainActivity", "TestDataDebug Chat: " + chatData!!.content)
//                        }
//                    }
//
//                // Get Messages Data
//                mRef.collection("chatrooms").document(id).collection("messages")
//                    .addSnapshotListener { snapshot, e ->
////                        chatroomsModels.clear()
//                        for (dc in snapshot!!.documentChanges) {
//                            val newDoc = dc.document.toObject(ChatModel::class.java)
//                            Log.d("MainActivity", newDoc.content)
//                        }
//                    }
            }

            setRoomRV(roomIds, chatroomsModels)

        }

        // Setup RV and Data
//        Log.d("MainActivity", "RoomData: " + roomIds[0])

    }


    private fun loginHardCode() {
        mAuth.signInWithEmailAndPassword("apip@gmail.com", "password").addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()
            }
        }
    }
}