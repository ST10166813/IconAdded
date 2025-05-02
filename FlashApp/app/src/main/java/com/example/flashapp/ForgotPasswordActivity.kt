package com.example.flashapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var usernameInput: EditText
    private lateinit var recoverButton: Button
    private lateinit var resultText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        usernameInput = findViewById(R.id.username_input)
        recoverButton = findViewById(R.id.recover_btn)
        resultText = findViewById(R.id.result_text)

        recoverButton.setOnClickListener {
            val username = usernameInput.text.toString().trim()
            if (username.isEmpty()) {
                Toast.makeText(this, "Please enter your username", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val backToLoginBtn = findViewById<Button>(R.id.backToLoginBtn)
            backToLoginBtn.setOnClickListener {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }

            val db = AppDatabase.getDatabase(this)
            val user = db.userDao().findUserByName(username)

            if (user != null) {
                resultText.text = "Your password is: ${user.password}"
            } else {
                resultText.text = "No user found with that username"
            }
        }
    }
}
