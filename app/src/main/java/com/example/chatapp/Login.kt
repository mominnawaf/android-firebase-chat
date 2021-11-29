package com.example.chatapp

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var login: Button
    private lateinit var signup: Button
    private lateinit var firebaseAuth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        firebaseAuth= FirebaseAuth.getInstance()
        login = findViewById(R.id.btnLogin)
        signup = findViewById(R.id.btnSignUp)
        email = findViewById(R.id.Email)
        password = findViewById(R.id.Pasword)
        signup.setOnClickListener {
            var intent = Intent(this, SignUp::class.java)
            startActivity(intent)
            finish()
        }
        login.setOnClickListener {
            login(email.text.toString(), password.text.toString())
        }
    }

    private fun login(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    var intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this,"${task.exception}",Toast.LENGTH_SHORT).show()
                }
            }
    }
}