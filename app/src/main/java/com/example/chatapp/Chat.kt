package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Chat : AppCompatActivity() {
    private lateinit var chatRecyclerView: RecyclerView
    private lateinit var btnSendMessage: Button
    private lateinit var messageBox: EditText
    private lateinit var dbRef : DatabaseReference
    private lateinit var messageList : ArrayList<Message>
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var senderRoom : String
    private lateinit var receiverRoom: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        chatRecyclerView = findViewById(R.id.chatRecyclerView)
        dbRef = FirebaseDatabase.getInstance().reference
        btnSendMessage =findViewById(R.id.btnSendMessage)
        messageBox = findViewById(R.id.messageBox)
        dbRef = FirebaseDatabase.getInstance().reference
        val name = intent.getStringExtra("name")
        val uuid = intent.getStringExtra("uuid")
        senderRoom = uuid?.plus(FirebaseAuth.getInstance().currentUser?.uid).toString()
        receiverRoom =FirebaseAuth.getInstance().currentUser?.uid.plus(uuid)
        supportActionBar?.title= name
        messageList = ArrayList()
        messageAdapter = MessageAdapter(this,messageList)
        chatRecyclerView.layoutManager = LinearLayoutManager(this)
        chatRecyclerView.adapter= messageAdapter
        dbRef.child("chats").child(senderRoom).child("messages").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                messageList.clear()
                for (obj in snapshot.children){
                    val message = obj.getValue(Message::class.java)
                    messageList.add(message!!)

                }
                messageAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        btnSendMessage.setOnClickListener {
            var message = messageBox.text.toString()
            val messageObj = Message(message,uuid.toString())
            dbRef.child("chats").child(senderRoom).child("messages").push().setValue(messageObj).addOnSuccessListener {
                dbRef.child("chats").child(receiverRoom).child("messages").push().setValue(messageObj)
            }
            messageBox.text= null
        }
    }
}