package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignUp : AppCompatActivity() {
    private lateinit var name: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var login: Button
    private lateinit var signUp: Button
    private lateinit var firebaseAuth :FirebaseAuth
    private lateinit var dBRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
        setContentView(R.layout.activity_sign_up)
        email = findViewById(R.id.Email)
        password = findViewById(R.id.Password)
        name = findViewById(R.id.Name)
        login = findViewById(R.id.btnLogin)
        signUp =findViewById(R.id.btnSignUp)
        login.setOnClickListener {
            var intent = Intent(this,Login::class.java)
            startActivity(intent)
            finish()
        }
        signUp.setOnClickListener {
            signup(name.text.toString(), email.text.toString(),password.text.toString())
        }
    }
    private fun signup(name:String,email:String,password:String){
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    AddUserInDb(name,email, firebaseAuth.currentUser?.uid)
                    // Sign in success, update UI with the signed-in user's information
                    var intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this,"${task.exception}",Toast.LENGTH_SHORT).show()
                    // If sign in fails, display a message to the user.
                }
            }
    }
    private fun AddUserInDb(name: String, email: String, uuid: String?) {
        dBRef = FirebaseDatabase.getInstance().reference
        if (uuid != null) {
            dBRef.child("user").child(uuid).setValue(User(name,email,uuid))
        }

    }
}