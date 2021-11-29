package com.example.chatapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserAdapter( private val context : Context,private val userList:ArrayList<User>):RecyclerView.Adapter<UserAdapter.ViewHolder>(){
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var nameText = itemView.findViewById<TextView>(R.id.Name)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view : View = LayoutInflater.from(context).inflate(R.layout.user_layout,parent,false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var currentUser = userList[position]
        holder.nameText.text=currentUser.name
        holder.itemView.setOnClickListener{
            var intent = Intent(context,Chat::class.java)
            intent.putExtra("uuid",currentUser.uuid)
            intent.putExtra("name",currentUser.name)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

}