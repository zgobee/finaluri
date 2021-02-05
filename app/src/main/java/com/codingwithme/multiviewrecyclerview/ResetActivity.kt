package com.codingwithme.multiviewrecyclerview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class ResetActivity : AppCompatActivity() {

    private lateinit var inputEmail: EditText
    private lateinit var sendButton: Button
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset)
        mAuth = FirebaseAuth.getInstance()

        inputEmail = findViewById(R.id.resetEmailEditText)
        sendButton = findViewById(R.id.sendEmailButton)

        sendButton.setOnClickListener {
            val email = inputEmail.text.toString()
            if (email.isEmpty()) {
                Toast.makeText(this, "ველი ცარიელია!", Toast.LENGTH_SHORT).show()
            }else {
                mAuth.sendPasswordResetEmail(email).addOnCompleteListener{ task ->
                    if (task.isSuccessful) {
                        startActivity(Intent(this, MainActivity::class.java))
                    }else {
                        Toast.makeText(this, "error!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}