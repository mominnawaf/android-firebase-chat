package com.example.chatapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(val context: Context, val messagelist: ArrayList<Message>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    class SendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sendView = itemView.findViewById<TextView>(R.id.sendText)
    }

    class ReceiveHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val receiveView = itemView.findViewById<TextView>(R.id.receiveText)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 1) {
            val view = LayoutInflater.from(context).inflate(R.layout.receive, parent, false)
            ReceiveHolder(view)
        } else {
            val view = LayoutInflater.from(context).inflate(R.layout.send, parent, false)
            SendViewHolder(view)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage = messagelist[position]
        if (holder.javaClass == SendViewHolder::class.java) {
            var viewHolder = holder as SendViewHolder
            holder.sendView.text = currentMessage.message
        } else {
            val viewHolder = holder as ReceiveHolder
            holder.receiveView.text = currentMessage.message
        }
    }

    override fun getItemViewType(position: Int): Int {
        var currentMessage = messagelist[position]
        return if (FirebaseAuth.getInstance().currentUser?.uid == currentMessage.senderId) {
            1
        } else {
            0
        }

    }

    override fun getItemCount(): Int {
        return messagelist.size
    }
}